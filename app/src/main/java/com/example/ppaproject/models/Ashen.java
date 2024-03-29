package com.example.ppaproject.models;

public class Ashen {

        private String email;
        private String password;
        private String phoneNumber;
        private String fullName;
        private String address;
        private String nic;
        private String vehicleRegNumber;
        private String insuranceHotline;
        private String insuranceNumber;
        private String firstTrusteeName;
        private String firstTrusteePhoneNumber;


        public Ashen(String email, String password, String phoneNumber, String fullName, String address, String nic, String vehicleRegNumber, String insuranceHotline, String insuranceExpiryDate, String firstTrusteeName, String firstTrusteePhoneNumber) {
            this.email = email;
            this.password = password;
            this.phoneNumber = phoneNumber;
            this.fullName = fullName;
            this.address = address;
            this.nic = nic;
            this.vehicleRegNumber = vehicleRegNumber;
            this.insuranceHotline = insuranceHotline;
            this.insuranceNumber = insuranceExpiryDate;
            this.firstTrusteeName = firstTrusteeName;
            this.firstTrusteePhoneNumber = firstTrusteePhoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getNic() {
            return nic;
        }

        public void setNic(String nic) {
            this.nic = nic;
        }

        public String getVehicleRegNumber() {
            return vehicleRegNumber;
        }

        public void setVehicleRegNumber(String vehicleRegNumber) {
            this.vehicleRegNumber = vehicleRegNumber;
        }

        public String getInsuranceHotline() {
            return insuranceHotline;
        }

        public void setInsuranceHotline(String insuranceHotline) {
            this.insuranceHotline = insuranceHotline;
        }

        public String getInsuranceExpiryDate() {
            return insuranceNumber;
        }

        public void setInsuranceExpiryDate(String insuranceExpiryDate) {
            this.insuranceNumber = insuranceExpiryDate;
        }

        public String getFirstTrusteeName() {
            return firstTrusteeName;
        }

        public void setFirstTrusteeName(String firstTrusteeName) {
            this.firstTrusteeName = firstTrusteeName;
        }

        public String getFirstTrusteePhoneNumber() {
            return firstTrusteePhoneNumber;
        }

        public void setFirstTrusteePhoneNumber(String firstTrusteePhoneNumber) {
            this.firstTrusteePhoneNumber = firstTrusteePhoneNumber;
        }

    }




