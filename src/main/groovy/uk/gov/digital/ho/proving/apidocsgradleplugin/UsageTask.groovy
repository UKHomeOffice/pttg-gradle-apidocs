package uk.gov.digital.ho.proving.apidocsgradleplugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @Author Home Office Digital
 */
class UsageTask extends DefaultTask {

    String description = "Describe how to use the plugin"
    String group = "help"

    @TaskAction
    def usageReport() {
        println "\n" +
            "************************************ USAGE ********************************************\n" +
            "pttgApiDocsGradle plugin usage guide\n" +
            "\n" +
             "This plugin applies the following configuration\n" +
            "\n" +
            "Plugins:\n" +
            "    Asciidoctor\n" +
            "\n" +
            "Repositories:\n" +
            "    maven - https://repo.spring.io/libs-milestone:\n" +
            "\n" +
            "Dependencies:\n" +
            "    spring-restdocs-core\n" +
            "    spring-restdocs-restassured\n" +
            "\n" +
            "Source Sets:\n" +
            "   doc\n" +
            "\n" +
            "The following tasks are added\n" +
            "\n" +
            "    apiDocTest : execute the api documentation tests\n" +
            "\n" +
            "    generateApiDocs: execute the documentation tests and generate the final HTML and PDF documentation\n" +
            "\n" +
            "    buildWithApiDocs: perform a full build and add the generated documentation to the final jar\n" +
            "\n" +
            "The plugin expects a convetional layout of source adoc files and tests\n" +
            "The standard layout can be modified using any of the following properties\n" +
            "\n" +
            "    apidocs{\n" +
            "        testSrcPattern = 'apidocs/**'\n" +
            "        sourceDocsDir = 'src/doc'\n" +
            "        jarDocsDir = 'static/docs'\n" +
            "        snippetsDir = 'build/generated-snippets'\n" +
            "    }\n" +
            "\n" +
            "See full details in the plugin readme in github at https://github.com/UKHomeOffice/pttg-gradle-apidocs"

    }
}
