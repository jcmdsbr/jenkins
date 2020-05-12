import jenkins.model.Jenkins
import org.csanchez.jenkins.plugins.kubernetes.ContainerTemplate
import org.csanchez.jenkins.plugins.kubernetes.KubernetesCloud
import org.csanchez.jenkins.plugins.kubernetes.PodTemplate

println "############################ KUBERNETES CLOUDs SETUP ############################"

def jenkins = Jenkins.getInstanceOrNull()
def cloudList = jenkins.clouds

def home_dir = System.getenv("JENKINS_HOME")
def properties = new ConfigSlurper().parse(new File("$home_dir/config/clouds.properties").toURI().toURL())

properties.kubernetes.each { cloudKubernetes ->
    println ">>> Kubernetes Cloud Setting up: " + cloudKubernetes.value.get('name')
    def kubernetesCloud = createKubernetesCloud(cloudKubernetes, new ArrayList<PodTemplate>())
    cloudList.add(kubernetesCloud)
}

jenkins.save()

println("Clouds Adicionadas: " + Jenkins.getInstanceOrNull().clouds.size())

def createKubernetesCloud(cloudKubernetes, podTemplateList) {
    def serverUrl = System.getenv("KUBERNETES_SERVER_URL")
    def jenkinsUrl = System.getenv("JENKINS_SERVER_URL")
    def kubernetesCloud = new KubernetesCloud(
            cloudKubernetes.value.get('name'),
            podTemplateList,
            serverUrl,
            cloudKubernetes.value.get('namespace'),
            jenkinsUrl,
            cloudKubernetes.value.get('containerCapStr'),
            cloudKubernetes.value.get('connectTimeout'),
            cloudKubernetes.value.get('readTimeout'),
            cloudKubernetes.value.get('retentionTimeout') )
    return kubernetesCloud
}
