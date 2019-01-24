
x= true
node {
    if (x) {
        stage "Checkout Source", {
            checkout scm
        }

        stage "Build Artifacts", {
            sh './gradlew clean assemble'

            //Cleanup
            cleanup()

            //Generate MD5 hash
            writeMd5()

            //Write last git commit log to file
            writeGitlog()

            //Save artifacts
            archiveArtifacts()
        }
    } 

        } catch (e) {
            
            throw e
        }
    }
}

def archiveArtifacts() {
    archive 'build/libs/*.war'
    archive 'build/libs/checksum.md5'
    archive 'build/libs/gitLog.txt'
}

def cleanup() {
    try {
        sh(script:'rm build/libs/*.original build/libs/*.md5 build/libs/gitLog.txt', returnStatus:false)
    } catch (ignore) {
    }
}

def writeMd5() {
    hash = sh(returnStdout:true, returnStatus:false, script:'md5sum build/libs/*.war').trim()
    writeFile file:'build/libs/checksum.md5', text:hash.split(" ")[0] + "\n"
}

def writeGitlog() {
    def gitLog = sh(script:'git log -n 1', returnStdout:true, returnStatus:false).trim()
    writeFile file:'build/libs/gitLog.txt', text:gitLog
}

