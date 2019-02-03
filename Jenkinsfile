pipeline {

  environment {
    
    // Project properties
    projectName = '${artifactId}'
    
    // Docker properties
    registryURL = '/** INSERT YOUR REGISTRY URL **/'
    registryCredential = '/** INSERT YOUR REGISTRY CREDENTIAL **/'
    
    // OpenShift properties
    openShiftCredentials = 'KUBECONFIG_CONTENT_CREDENTIALS'
    openShiftCluster = '/** INSERT YOUR OPENSHIFT CLUSTER **/'
    openShiftProjectName = '/** INSERT YOUR OPENSHIFT PROJECT NAME **/'
    
  }
  
  agent any
  
  stages {

    stage('Compiling application') {
      steps {
        script {
          echo "Compiling application"
          sh 'mvn clean package'
        }
      }
    }

    stage('Building Docker image') {
      steps {
        script {
          echo "Building Docker image"
          docker.withRegistry(registryURL + projectName, registryCredential) {
            def customImage = docker.build(projectName + ':latest')
            customImage.push()
            echo "Docker image pushed"
          }
        }
      }
    }
    
    stage('Publishing on OpenShift') {
      steps {
        script {
          echo "Publishing on OpenShift"
          withCredentials([kubeconfigContent(credentialsId: openShiftCredentials, variable: 'KUBECONFIG_CONTENT')]){
            sh '''echo "$KUBECONFIG_CONTENT" > kubeconfig'''
            // Update OpenShift configuration
            sh("oc apply --kubeconfig=kubeconfig -f config/deployment.yml")
            // Try to expose route, otherwise ignore the error
            sh '''! oc expose --kubeconfig=kubeconfig svc/"$projectName"'''
            sh '''oc rollout latest --kubeconfig=kubeconfig dc/"$projectName"'''
          }
        }
      }
    }
	
    stage('Verifying') {
      steps {
        script {
          echo "Verifying"
          sh '''oc rollout status dc/"$projectName"'''
        }
      }
    }

  }

}