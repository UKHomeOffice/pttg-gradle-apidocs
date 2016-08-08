package uk.gov.digital.ho.proving.apidocsgradleplugin

/**
 * @Author Home Office Digital
 */
class ApiDocsPluginExtension {

    def String testSrcPattern = 'apidocs/**'

    def String sourceDocsDir = 'src/doc'

    def String jarDocsDir = 'static/docs'

    def String snippetsDir = 'build/generated-snippets'

    def String jarAppendix = 'docs'
}
