pipeline {
 agent any
 tools {
   maven 'Maven 3.8.8'       
   jdk 'Temurin JDK 17'
 }

 environment {
   SONARQUBE_SERVER = 'SonarQube'
   DOCKER_IMAGE = 'rizkirahmnn/springboot-app:latest'
 }

 stages {
   stage('Checkout') {
     steps {
       git url: 'https://github.com/RizkiRahmann/challenge-day35', branch: 'master'
     }
   }

   stage('Unit Test & Coverage') {
     steps {
       sh 'mvn package'
     }
     post {
       always {
         junit 'target/surefire-reports/*.xml'
       }
     }
   }

   stage('Static Code Analysis (SAST) via Sonar') {
     steps {
       sh """
           mvn clean compile sonar:sonar \
             -Dsonar.projectKey=springboot \
             -Dsonar.projectName='springboot' \
             -Dsonar.host.url=http://sonarqube:9000 \
             -Dsonar.token=sqp_752bc5c949cd69e421c607c5df9bc7752085fed4
       """
     }
   }

   stage('Build & Push Docker') {
     steps {
       withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', 
                                        usernameVariable: 'DOCKER_USER', 
                                        passwordVariable: 'DOCKER_PASS')]) {
         sh "docker login -u ${DOCKER_USER} -p ${DOCKER_PASS}"
         sh "docker build -t ${DOCKER_IMAGE} ."
         sh "docker push ${DOCKER_IMAGE}"
       }
     }
   }
 }

 post {
   success {
     echo "Pipeline berhasil 🚀"
   }
   failure {
     echo "Pipeline gagal 💥"
   }
 }
}