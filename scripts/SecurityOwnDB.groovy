import java.lang.System
import jenkins.model.*
import hudson.security.*
import hudson.model.*

def home_dir = System.getenv("JENKINS_HOME")
def properties = new ConfigSlurper().parse(new File("$home_dir/config/authentication.properties").toURI().toURL())

if(properties.owndb.enabled) {

  HudsonPrivateSecurityRealm realm = new HudsonPrivateSecurityRealm(false)
  properties.owndb.users.each() { key, value ->
    File passwordFile = new File(value.path)
    realm.createAccount(value.userId, passwordFile.text.trim())
  }

  Jenkins.instance.setSecurityRealm(realm)
  Jenkins.instance.save()

  Descriptor dslSecurity = Jenkins.instance.getDescriptor('javaposse.jobdsl.plugin.GlobalJobDslSecurityConfiguration')
 
  if(dslSecurity != null) {
    dslSecurity.setUseScriptSecurity(false)
    Jenkins.instance.save()
  }

}
 
