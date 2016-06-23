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

        try {
            def usage = this.getClass().getResource('/usage/usage.txt').text
            println usage
        } catch (Exception e) {
            println "Error loading usage report"
            e.printStackTrace()
        }

    }
}
