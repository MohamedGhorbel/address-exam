pipeline {
    agent any
    environment {
        EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE = credentials('EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE')
    }
    stages {


        stage('build address svc') {
            steps {
                bat 'mvn package'
            }
        }
        stage('build docker image') {
            steps {
                bat "docker build -t address-svc ."
            }
        }
       stage('push to registry'){
              steps{
                    bat " docker login --username ${EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE_USR} --password ${EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE_PSW}"
                    bat "docker tag address-svc:${env.BUILD_ID} ${EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE_USR}/address-svc:${env.BUILD_ID}"
                    bat "docker push ${EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE_USR}/address-svc:${env.BUILD_ID}"
                    }

              }

        }
        stage('deploy') {
            steps {

                bat 'docker-compose -p address-svc up -d '
                retry(3) {
                    timeout(3) {
                     bat "curl http://localhost:8083/address-svc/api/address/getById"
              }
            }
            }
        }
    }
    post {
        always {
            junit 'target/*reports/*.xml'
            /*emailext attachLog: true, body: 'test success', subject: 'test notification', to: 'hablous2@gmail.com'*/
        }

        failure {
            echo 'This will run only if failed'
        }

    }
}

