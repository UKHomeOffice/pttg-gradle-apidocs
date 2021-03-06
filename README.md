## Gradle plugin that adds Spring Rest API documentation support

### Use this plugin in your gradle build by:

1. Adding a buildscript dependency

```
buildscript {
       repositories{
            maven { url "https://github.com/UKHomeOffice/pttg-gradle-repo/raw/master/releases" }
            jcenter()
       }
       dependencies {
           classpath 'pttg-gradle-common:pttgApiDocsGradle:1.1.RELEASE'
       }
}
```

2. Applying the plugin

```
apply plugin: 'pttgApiDocsGradle'
```
! NOTE - This plugin expects the java plugin to be applied first.
If you are using the pttgCommonGradlePlugin, ensure it is applied above this one.

3. User guide
See the following documentation. In your project you can also execute the 'pttgApiDocsGradlePluginUsage' task to
see usage instructions and version information etc.

4. Configuring the plugin

Customise configuration using the 'apidocs' extension block and override any properties you wish to change.
The available properties and their defaults are shown in this sample:

```
apidocs{
    testSrcPattern = 'apidocs/**'
    sourceDocsDir = 'src/doc'
    jarDocsDir = 'static/docs'
    snippetsDir = 'build/generated-snippets'
    jarAppendix = 'docs'
}
```

where
 * testSrcPattern = pattern for the api test classes eg this is for tests under src/test/java/apidocs
 * sourceDocsDir = location of your 'asciidoc' and 'resources' folders containing asciidoc files (eg your index.adoc) and template overrides
 * jarDocsDir = location to put the generated documentation (HTML and PDF) in the project jar
 * snippetsDir = working directory where spring-rest-docs will generate adoc fragments from your test cases
 * jarAppendix = appendix to append to the jar name
 
5. Using the plugin in conjunction with SpringBoot

The buildWithApiDocs task generates a jar containing the project outputs and the generated api docs.
To produce a Spring Boot executable jar, you must then execute the bootRepackage task.
These steps can be combined with a custom task such as the following:

```
task buildSpringBootWithApiDocs(type: BootRepackage, dependsOn: buildWithApiDocs) {
    group 'build'
    description 'Builds the jar as a Spring Boot executable jar containing the api docs'
}
```

6. Modifying the jar manifest

The buildWithApiDocs task generates a default jar manifest. If you have customised the project's jar manifest then those
customisations should be passed to the buildWithApiDocs task by extracting a shared manifest such as:

```
version = blah

ext.sharedManifest = manifest {
    attributes(
        'Implementation-Title': "${jar.baseName}",
        'Implementation-Version': version
    )
}
```

which can then be re-used in your jar task or the buildWithApiDocs task as follows:

```
buildWithApiDocs{
    manifest = project.manifest {
        from sharedManifest
    }
}
```
 

### What this plugin gives your build automatically

1. Applies the spring-rest-doc and asciidoctor plugins

2. Applies the spring-rest-docs dependencies and repositories

3. Creates a new source set, 'doc', to hold your asciidoc files

4. Excludes api doc tests from the regular unit test task

5. Creates the following new tasks for working with spring rest docs
   1. verification / apiDocTest : run the api doc test cases
   2. documentation / generateApiDocs : run the api doc test cases and generate the final HTML and PDF documentation
   3. build / buildWithApiDocs : perform a full build including the api doc tests, adding the generated documentation to a copy of the final jar 


### Development

1. Increment the minor version number (still needs to be RELEASE because I can't figure out how to make the git-repo plugin support snapshots)
2. Make your changes
3. You can use publishMavenJavaPublicationToMavenLocal to deploy the change to your local maven repo
   1. Note however that this won't work if your changes add new transitive third-party dependencies
4. Test consumption of the plugin from another project by
   1. Adding mavenLocal() to your buildscript repositories to consume form local repo
   2. using the new version number for the plugin in your buildscript dependencies
   3. You may well need to execute ``` ./gradlew --refresh-dependencies``` to pick up changes, then refresh your IDE
