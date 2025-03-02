/* Copyright 2018 EPAM Systems.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

 See the License for the specific language governing permissions and
 limitations under the License.*/

package com.epam.edp.platform

def getPlatformImpl(script) {
    def platformType = System.getenv("PLATFORM_TYPE")
    if (!platformType)
        script.error("[JENKINS][ERROR] Mandatory environment variable PLATFORM_TYPE is not defined")
    switch (platformType.toLowerCase()) {
        case PlatformType.OPENSHIFT.value:
            return new Openshift(script: script)
        case PlatformType.KUBERNETES.value:
            return new Kubernetes(script: script)
        default:
            script.error("[JENKINS][ERROR] Failed to determine platform type")
    }
}

interface Platform {
    def getJsonPathValue(object, name, jsonPath)
    def getJsonValue(object, name)
    def getExternalEndpoint(name)
    def apply(fileName)
    def copyToPod(source, destination, podName,podNamespace, podContainerName)
    def deleteObject(objectType, objectName, force)
    def getObjectStatus(objectType, objectName)
}