pipeline {
    agent any
    tools {
        maven 'M3' 
    }
    stages {
        stage('Example') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}
