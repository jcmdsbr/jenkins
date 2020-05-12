import java.lang.System
import hudson.model.*
import jenkins.model.*
import java.net.InetAddress

def home_dir = System.getenv("JENKINS_HOME")
GroovyShell shell = new GroovyShell()

def helpers = shell.parse(new File("$home_dir/init.groovy.d/Helpers.groovy"))
def properties = new ConfigSlurper().parse(new File("$home_dir/config/globals.properties").toURI().toURL())

Jenkins.instance.setNumExecutors(properties.global.numExecutorsOnMaster)
Jenkins.instance.setQuietPeriod(properties.global.scmQuietPeriod)
Jenkins.instance.setScmCheckoutRetryCount(properties.global.scmCheckoutRetryCount)

jlc = JenkinsLocationConfiguration.get()

if (properties.global.jenkinsRootUrl) {
  jlc.setUrl(properties.global.jenkinsRootUrl)
} else {
  def ip = InetAddress.localHost.getHostAddress()
  jlc.setUrl("http://$ip:8080")
}

jlc.save()

properties.global.variables.each { key, value ->
  helpers.addGlobalEnvVariable(Jenkins, key, value)
}

