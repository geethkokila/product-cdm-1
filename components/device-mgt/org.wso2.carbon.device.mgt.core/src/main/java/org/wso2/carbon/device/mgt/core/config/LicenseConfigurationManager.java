/*
 *
 *   Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */

package org.wso2.carbon.device.mgt.core.config;

import org.w3c.dom.Document;
import org.wso2.carbon.device.mgt.common.LicenseManagementException;
import org.wso2.carbon.device.mgt.core.DeviceManagementConstants;
import org.wso2.carbon.device.mgt.core.util.DeviceManagerUtil;
import org.wso2.carbon.utils.CarbonUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class LicenseConfigurationManager {

    private LicenseManagementConfig licenseMgtConfig;
    private static LicenseConfigurationManager licenseConfigManager;

    private static final String licenseMgtConfigXMLPath = CarbonUtils.getCarbonConfigDirPath() + File.separator +
            DeviceManagementConstants.Common.DEFAULT_LICENSE_CONFIG_XML_NAME;

    public static LicenseConfigurationManager getInstance() {
        if (licenseConfigManager == null) {
            synchronized (LicenseConfigurationManager.class) {
                if (licenseConfigManager == null) {
                    licenseConfigManager = new LicenseConfigurationManager();
                }
            }
        }
        return licenseConfigManager;
    }

    public synchronized void initConfig() throws LicenseManagementException {

        //catch generic exception.if any exception occurs wrap and throw LicenseManagementException
        try {
            File licenseMgtConfig = new File(licenseMgtConfigXMLPath);
            Document doc = DeviceManagerUtil.convertToDocument(licenseMgtConfig);

            /* Un-marshaling License Management configuration */
            JAXBContext cdmContext = JAXBContext.newInstance(LicenseManagementConfig.class);
            Unmarshaller unmarshaller = cdmContext.createUnmarshaller();
            this.licenseMgtConfig = (LicenseManagementConfig) unmarshaller.unmarshal(doc);
        } catch (Exception e) {
            throw new LicenseManagementException("Error occurred while initializing RSS config", e);
        }
    }

    public LicenseManagementConfig getLicenseMgtConfig() {
        return licenseMgtConfig;
    }


}
