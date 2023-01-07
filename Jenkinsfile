pipeline {
    agent any
    environment {
        EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE = credentials('EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE')
    }
    stages {
           stage('pre-build') {
                   steps {
                       echo'List des variable environment'
                       bat 'SET'
                   }
               }
           stage('build') {
               steps {
                   bat 'mvn package'
               }
           }
           stage('build docker image') {
               steps {
                  bat "docker build -t address-svc ."
               }
           }
            stage('push to registory') {
                       steps {
                          bat "docker login --username ${mohamed.ghorbel20172018@gmail.com} --password ${g14765468}"
                          bat "docker tag address-svc ${mohamed.ghorbel20172018@gmail.com}/address-svc"
                          bat "docker push ${mohamed.ghorbel20172018@gmail.com}/address-svc"
                       }
                   }
           stage('deploy') {
               steps {
                   bat 'docker-compose -p  address-svc up -d '
                   retry(3) {
                    timeout(2) {
                     bat "curl http://localhost:8083/address-svc/api/address/getById"
                     }
                }
               }
           }
        }

       post {

           success {
               echo 'This will run only if successful'
           }
           failure {
               echo 'This will run only if failed'
           }
           unstable {
               echo 'This will run only if the run was marked as unstable'
           }
           changed {
               echo 'This will run only if the state of the Pipeline has changed'
               echo 'For example, if the Pipeline was previously failing but is now successful'
           }
       }
}