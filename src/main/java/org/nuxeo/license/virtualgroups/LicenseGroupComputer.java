package org.nuxeo.license.virtualgroups;

import java.util.ArrayList;
import java.util.List;

import org.nuxeo.ecm.platform.computedgroups.AbstractGroupComputer;
import org.nuxeo.ecm.platform.usermanager.NuxeoPrincipalImpl;
import org.nuxeo.license.api.LicenseManager;
import org.nuxeo.runtime.api.Framework;

public class LicenseGroupComputer extends AbstractGroupComputer {

    public static final String License_GROUP_PREFIX = "License_GROUP_";

    public static String getGroupNameForLicenseType(String licenseType) {
        return License_GROUP_PREFIX + licenseType;
    }

    @Override
    public List<String> getAllGroupIds() throws Exception {

        List<String> allGroups = new ArrayList<String>();
        List<String> licenses = Framework.getLocalService(LicenseManager.class).getLicenseTypes();
        for (String license : licenses) {
            allGroups.add(getGroupNameForLicenseType(license));
        }
        return null;
    }

    @Override
    public List<String> getGroupMembers(String groupName) throws Exception {

        if (groupName != null && groupName.startsWith(License_GROUP_PREFIX)) {
            String licenseType = groupName.substring(License_GROUP_PREFIX.length());
            return Framework.getLocalService(LicenseManager.class).getLicensedUsers(
                    licenseType);
        } else {
            return new ArrayList<String>();
        }
    }

    @Override
    public List<String> getGroupsForUser(NuxeoPrincipalImpl user)
            throws Exception {
        List<String> groups = new ArrayList<String>();
        for (String license : Framework.getLocalService(LicenseManager.class).getLicensesForUser(
                user.getName())) {
            groups.add(getGroupNameForLicenseType(license));
        }
        return groups;
    }

    @Override
    public List<String> getParentsGroupNames(String group) throws Exception {
        return new ArrayList<String>();
    }

    @Override
    public List<String> getSubGroupsNames(String arg0) throws Exception {
        return new ArrayList<String>();
    }

}
