import jenkins.model.*
import org.jenkinsci.plugins.*

def instance    = Jenkins.getInstance()
def globalProps = instance.getGlobalNodeProperties()
def props       = globalProps.getAll(hudson.slaves.EnvironmentVariablesNodeProperty.class)

def environments = [
    "ChocoToolBase": "C:\\ProgramData\\chocolatey\\bin",
    "CI_BASE": "C:\\CI_BASE",
    "MSBUILD40": "C:\\Windows\\Microsoft.NET\\Framework\\v4.0.30319\\msbuild.exe",
    "NUnit264_32": "C:\\Program Files\\NUnit 2.6.4\\bin"
    ]

environments.each {
  if (props.isEmpty()) {
    globalProps.add(new hudson.slaves.EnvironmentVariablesNodeProperty(
      new hudson.slaves.EnvironmentVariablesNodeProperty.Entry(it.key, it.value )
    ))
  } else {
    props.get(0).envVars.put(it.key, it.value)
  }
}

instance.save()