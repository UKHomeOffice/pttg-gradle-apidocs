package uk.gov.digital.ho.proving

import org.asciidoctor.gradle.AsciidoctorPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.testing.Test

/**
 * @Author Home Office Digital
 */
class ApiDocsGradle implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.create("apidocs", ApiDocsPluginExtension)

        project.with {

            repositories {
                maven { url 'https://repo.spring.io/libs-milestone' }
            }

            plugins.apply(AsciidoctorPlugin)

            dependencies {
                testCompile libraries.springrestdocs
            }

            sourceSets {
                doc {
                    resources.srcDir project.file("${project.apidocs.sourceDocsDir}/resources")
                }
            }

            test {
                exclude "${project.apidocs.testSrcPattern}"
            }

            task('apiDocTest', type: Test) {
                description 'Runs the API documentation tests (generates doc snippets to build/generated-snippets but does not process into HTML)'
                group 'verification'
                include "${project.apidocs.testSrcPattern}"
                classpath = sourceSets.test.runtimeClasspath + project.files("${project.apidocs.sourceDocsDir}/resources")
            }

            task('generateApiDocs', dependsOn: ['apiDocTest', 'asciidoctor']) {
                description 'Generates the API documentation - look in build/asciidoc for the output'
                group 'documentation'
            }

            asciidoctor {
                description 'You should use the generateApiDocs task instead'
                attributes 'snippets': project.file("${project.apidocs.snippetsDir}")
                inputs.dir project.file("${project.apidocs.snippetsDir}")
                outputDir 'build/asciidoc'
                sourceDir "${project.apidocs.sourceDocsDir}/asciidoc"
                backends 'pdf', 'html5'
                mustRunAfter 'apiDocTest'
            }

            task('buildWithApiDocs', type: Jar, dependsOn: ['generateApiDocs', 'build']) {
                description 'Same as build but also generates API docs and adds them to the jar'
                group 'build'

                destinationDir = new File("build/libs/")

                project.afterEvaluate { // Wait for regular jar task to be evaluated first

                    baseName = jar.baseName
                    appendix = "api+docs"
                    version = jar.version

                    manifest { attributes('Main-Class': "$mainClassName") }

                    from zipTree(jar.archivePath)
                    from("${asciidoctor.outputDir}/html5/index.html") { into "${project.apidocs.jarDocsDir}" }
                    from("${asciidoctor.outputDir}/pdf/index.pdf") { into "${project.apidocs.jarDocsDir}" }
                }
            }
        }
    }


}