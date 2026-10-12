pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn -B -DskipTests compile'
                    } else {
                        bat 'mvn -B -DskipTests compile'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn -B test'
                    } else {
                        bat 'mvn -B test'
                    }
                }
            }
        }
        stage('Package') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn -B -DskipTests package'
                    } else {
                        bat 'mvn -B -DskipTests package'
                    }
                }
            }
        }
        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
}