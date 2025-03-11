package de.hsc.bewerberaufgabe.bo;

import java.util.List;

public class LicenseValidator {

    public static void checkLicense(Library library) {
        for (License license : library.getLicenses()) {
            if (license == null || license.getName().trim().isEmpty()){
                System.out.println("no license found for: " + library.getArtifactId());
            }
            if (!isLicenseAllowed(license)) {
                System.out.println("unauthorized license: " + license.getName().trim());
            }
        }
    }

    public static boolean isLicenseAllowed(License license) {
        for(BlockedLicenses blocked : BlockedLicenses.values()){
            if (license.getName().contains(blocked.getName())){
                return false;
            }
        }
        return true;
    }
}
