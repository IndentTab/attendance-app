pipeline {
    agent any

    environment {
        IMAGE_NAME = "nodejs-demo-app"
        CONTAINER_NAME = "nodejs-demo-container"
    }

    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/<your-username>/<your-repo-name>.git'
            }
        }

        stage('Install Dependencies') {
            steps {
                sh 'npm install'
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Optional — if you have tests defined in package.json
                    sh 'npm test || echo "No tests defined"'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t $IMAGE_NAME .'
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    // Stop old container if running
                    sh 'docker rm -f $CONTAINER_NAME || true'

                    // Run the new one
                    sh 'docker run -d -p 3000:3000 --name $CONTAINER_NAME $IMAGE_NAME'
                }
            }
        }
    }

    post {
        success {
            echo '✅ Build and deployment successful!'
        }
        failure {
            echo '❌ Build failed.'
        }
    }
}
