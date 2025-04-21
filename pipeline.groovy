pipeline {
    agent any
    stage {
        stage ('code-pull') {
            steps {
                git branch: 'dev', url: 'https://github.com/srngx/project-backend.git'
            }
        }
        stage ('code-Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage ('Deploy-k8s') {
            steps {
                sh '''
                    docker build . -t archsarangx/spring-backend:latest
                    docker push archsarangx/spring-backend:latest
                    docker rmi archsarangx/spring-backend:latest

                    kubectl apply -f ./deploy/deployment.yaml
                '''
                
            }
        }
        
    }
}