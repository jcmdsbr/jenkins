def label = "worker-${UUID.randomUUID().toString()}"

podTemplate(label: label, containers: [
  containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true),
  containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl:v1.8.8', command: 'cat', ttyEnabled: true)
],
volumes: [
  hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
]) {
  node(label) { 
    stage('Build') {
      container('docker') {
        sh """
            docker pull redis
            """
      }
    }
    stage('Deploy Dev') {
      container('kubectl') {
        sh "kubectl get pods"
      }
    }
  }
}