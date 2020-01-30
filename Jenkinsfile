// 脚本式

def git_auth = "a867b441-2030-4ac3-81e8-ba5a348ab1e5"
def git_url = "http://192.168.116.200/root/webflux.git"
node {
   stage('code pull') {
    echo 'code pull'
    checkout([$class: 'GitSCM', branches: [[name: "*/${branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: "${git_auth}", url: "${git_url}"]]])
   }
   stage('build') {
    echo 'build project'
    sh label: '', script: '''
        cd ${moudle}
        pwd
        mvn clean package
    '''
   }
   stage('code checking'){
    scannerHome = tool 'sonarqube-scanner'
    withSonarQubeEnv('sonarqube'){
    sh label: '', script: '''
            cd ${moudle}
            pwd
            sh "${scannerHome}/bin/sonar-scanner -X"
    '''
    }
   }
}

// 声明式
//pipeline {
//   agent any
//
//   stages {
//      stage('pull code') {
//         steps {
//            echo 'pull code'
//            checkout([$class: 'GitSCM', branches: [[name: '*/${branch}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'a867b441-2030-4ac3-81e8-ba5a348ab1e5', url: 'http://192.168.116.200/root/wb_demo.git']]])
//         }
//      }
//      stage('code checking'){
//         steps {
//            script{
//                scannerHome = tool 'sonarqube-scanner'
//            }
//            withSonarQubeEnv('sonarqube'){
//                sh "${scannerHome}/bin/sonar-scanner -X"
//            }
//         }
//      }
//      stage('build') {
//         steps {
//            echo 'build project'
//            sh 'mvn clean package'
//         }
//      }
//      stage('publish project') {
//         steps {
//            echo 'publish project'
//            deploy adapters: [tomcat7(credentialsId: 'fbc4cc39-88ad-413b-afea-6a4ea402a355', path: '', url: 'http://192.168.116.200:8081/')], contextPath: 'pipeline', war: 'target/*.war'
//         }
//      }
//   }
//   post {
//      always {
//        // One or more steps need to be included within each condition's block.
//        emailext(
//            subject: '构建通知 - ${PROJECT_NAME}',
//            body: '${FILE,path="email.html"}',
//            to: '279505647@qq.com'
//        )
//      }
//   }
//}