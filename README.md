## Gradle plugin that adds Spring Rest API documentation support

### Use this plugin in your gradle build by:

1. Adding a buildscript dependency

```
buildscript {
       dependencies {
           classpath 'pttg-gradle-common:pttgApiDocsGradle:0.1'
       }
}
```

2. Applying the plugin

```
apply plugin: 'pttgApiDocsGradle'
```

3. Configuring the plugin

Customise configuration using the 'apidocs' extension block and override any properties you wish to change.
The available properties and their defaults are shown in this sample:

```
apidocs{
    testSrcPattern = 'apidocs/**'
    sourceDocsDir = 'src/doc'
    jarDocsDir = 'static/docs'
    snippetsDir = 'build/generated-snippets'
}
```

where
 * testSrcPattern = pattern for the api test classes eg this is for tests under src/test/java/apidocs
 * sourceDocsDir = location of your 'asciidoc' and 'resources' folders containing asciidoc files (eg your index.adoc) and template overrides
 * jarDocsDir = location to put the generated documentation (HTML and PDF) in the project jar
 * snippetsDir = working directory where spring-rest-docs will generate adoc fragments from your test cases

### What this plugin gives your build automatically

1. Applies the spring-rest-doc and asciidoctor plugins

2. Applies the spring-rest-docs dependencies and repositories

3. Creates a new source set, 'doc', to hold your asciidoc files

4. Excludes api doc tests from the regular unit test task

5. Creates the following new tasks for working with spring rest docs
   1. verification / apiDocTest : run the api doc test cases
   2. documentation / generateApiDocs : run the api doc test cases and generate the final HTML and PDF documentation
   3. build / buildWithApiDocs : perform a full build including the api doc tests, adding the generated documentation to a copy of the final jar 

