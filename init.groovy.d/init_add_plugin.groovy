import jenkins.model.*
import groovy.json.*

def instance  = Jenkins.getInstance()
def plugins   = [
                    "ace-editor",
                    "ant",
                    "antisamy-markup-formatter",
                    "apache-httpcomponents-client-4-api",
                    "authentication-tokens",
                    "bouncycastle-api",
                    "branch-api",
                    "build-pipeline-plugin",
                    "build-timeout",
                    "build-timestamp",
                    "cli-commander",
                    "cloud-stats",
                    "cloudbees-bitbucket-branch-source",
                    "cloudbees-folder",
                    "command-launcher",
                    "compress-artifacts",
                    "conditional-buildstep",
                    "copyartifact",
                    "credentials",
                    "credentials-binding",
                    "display-url-api",
                    "docker-commons",
                    "docker-workflow",
                    "durable-task",
                    "email-ext",
                    "envinject",
                    "envinject-api",
                    "external-monitor-job",
                    "favorite",
                    "file-operations",
                    "git",
                    "git-client",
                    "git-server",
                    "github",
                    "github-api",
                    "github-branch-source",
                    "github-organization-folder",
                    "global-post-script",
                    "gradle",
                    "groovy",
                    "groovy-postbuild",
                    "handlebars",
                    "htmlpublisher",
                    "icon-shim",
                    "jackson2-api",
                    "junit",
                    "klocwork",
                    "ldap",
                    "mailer",
                    "mapdb-api",
                    "matrix-auth",
                    "matrix-project",
                    "maven-plugin",
                    "mercurial",
                    "momentjs",
                    "msbuild",
                    "nant",
                    "notification",
                    "nunit",
                    "pam-auth",
                    "parameterized-trigger",
                    "pipeline-build-step",
                    "pipeline-github-lib",
                    "pipeline-graph-analysis",
                    "pipeline-input-step",
                    "pipeline-milestone-step",
                    "pipeline-model-api",
                    "pipeline-model-declarative-agent",
                    "pipeline-model-definition",
                    "pipeline-model-extensions",
                    "pipeline-rest-api",
                    "pipeline-stage-step",
                    "pipeline-stage-tags-metadata",
                    "pipeline-stage-view",
                    "plain-credentials",
                    "powershell",
                    "pubsub-light",
                    "rebuild",
                    "resource-disposer",
                    "rocketchatnotifier",
                    "run-condition",
                    "scm-api",
                    "script-security",
                    "slave-setup",
                    "sse-gateway",
                    "ssh-credentials",
                    "ssh-slaves",
                    "structs",
                    "subversion",
                    "timestamper",
                    "token-macro",
                    "variant",
                    "vstestrunner",
                    "windows-slaves",
                    "workflow-aggregator",
                    "workflow-api",
                    "workflow-basic-steps",
                    "workflow-cps",
                    "workflow-cps-global-lib",
                    "workflow-durable-task-step",
                    "workflow-job",
                    "workflow-multibranch",
                    "workflow-scm-step",
                    "workflow-step-api",
                    "workflow-support",
                    "ws-cleanup"
                ]


// load jenkins plugin list from extract file.
/*def slurper = new JsonSlurper()
def contents = new File("JenkinsPlugin.json").text
def result = slurper.parseText(contents)
*/


pm = instance.getPluginManager()
uc = instance.getUpdateCenter()

uc.updateAllSites()

def enablePlugin(pluginName) {
  if (! pm.getPlugin(pluginName)) {
    deployment = uc.getPlugin(pluginName).deploy(true)
    deployment.get()
  }

  def plugin = pm.getPlugin(pluginName)
  if (! plugin.isEnabled()) {
    plugin.enable()
  }

  plugin.getDependencies().each {
    enablePlugin(it.shortName)
  }
}

plugins.each {
  def plugin = pm.getPlugin(it)
  enablePlugin(it)
}
