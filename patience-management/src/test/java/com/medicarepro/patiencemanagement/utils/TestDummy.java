package com.medicarepro.patiencemanagement.utils;

import com.medicarepro.patiencemanagement.controller.dto.*;
import com.medicarepro.patiencemanagement.service.Gender;
import com.medicarepro.patiencemanagement.service.entity.*;
import com.medicarepro.patiencemanagement.service.mapping.ContractInformationMapping;
import com.medicarepro.patiencemanagement.service.mapping.DemographicInformationMapping;
import com.medicarepro.patiencemanagement.service.mapping.InsuranceInformationMapping;
import com.medicarepro.patiencemanagement.service.mapping.MedicalHistoryMapping;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TestDummy {


    private TestDummy() {
    }

    public static List<Patience> getAllPatients() {
        Patience johnDoe = Patience.builder()
                .patienceId(generatePatienceId())
                .build();

        DemographicInformation johnDoeDemographic = DemographicInformation.builder()
                .name("John Doe")
                .age(42)
                .timeOfBirth(LocalDate.of(1980, Month.JANUARY, 1))
                .married(false)
                .hasKids(true)
                .gender(Gender.MALE)
                .patience(johnDoe)
                .build();

        ContractInformation johnDoeContract = ContractInformation.builder()
                .address("123 Main Street, Cityville, State")
                .phoneNumber("(555) 123-4567")
                .email("john.doe@example.com")
                .patience(johnDoe)
                .build();

        InsuranceInformation johnDoeInsurance = InsuranceInformation.builder()
                .insuranceProvider("ABC Insurance")
                .insurancePolicyNumber(1234567890L)
                .coverageDateStart(LocalDate.of(2000, Month.JULY, 12))
                .coverageDateEnd(LocalDate.of(2010, Month.JULY, 12))
                .patience(johnDoe)
                .build();

        MedicalHistory johnDoeMedicalHistory = MedicalHistory.builder()
                .conditions(List.of("Hypertension", "Type 2 Diabetes"))
                .allergies(List.of("Penicillin"))
                .medications(Collections.emptyList())
                .surgeries(Collections.emptyList())
                .patience(johnDoe)
                .build();

        johnDoe.setDemographicInformation(johnDoeDemographic);
        johnDoe.setContractInformation(johnDoeContract);
        johnDoe.setInsuranceInformation(johnDoeInsurance);
        johnDoe.setMedicalHistory(johnDoeMedicalHistory);
        johnDoe.setDoctorIds(List.of(1L));


        Patience janeSmith = Patience.builder()
                .patienceId(generatePatienceId())
                .build();

        DemographicInformation janeSmithDemographic = DemographicInformation.builder()
                .name("Jane Smith")
                .age(29)
                .timeOfBirth(LocalDate.of(1995, Month.MARCH, 15))
                .married(false)
                .hasKids(false)
                .gender(Gender.FEMALE)
                .patience(janeSmith)
                .build();

        ContractInformation janeSmithContract = ContractInformation.builder()
                .address("456 Oak Street, Townsville, State")
                .phoneNumber("(555) 987-6543")
                .email("jane.smith@example.com")
                .patience(janeSmith)
                .build();

        InsuranceInformation janeSmithInsurance = InsuranceInformation.builder()
                .insuranceProvider("XYZ Healthcare")
                .insurancePolicyNumber(987654321L)
                .coverageDateStart(LocalDate.of(2005, Month.JANUARY, 1))
                .coverageDateEnd(LocalDate.of(2020, Month.DECEMBER, 31))
                .patience(janeSmith)
                .build();

        MedicalHistory janeSmithMedicalHistory = MedicalHistory.builder()
                .conditions(List.of("Asthma"))
                .allergies(Collections.emptyList())
                .medications(List.of("Inhaler"))
                .surgeries(Collections.emptyList())
                .patience(janeSmith)
                .build();

        janeSmith.setDemographicInformation(janeSmithDemographic);
        janeSmith.setContractInformation(janeSmithContract);
        janeSmith.setInsuranceInformation(janeSmithInsurance);
        janeSmith.setMedicalHistory(janeSmithMedicalHistory);
        janeSmith.setDoctorIds(List.of(2L));

        Patience maryJohnson = Patience.builder()
                .patienceId(generatePatienceId())
                .build();

        DemographicInformation maryJohnsonDemographic = DemographicInformation.builder()
                .name("Mary Johnson")
                .age(52)
                .timeOfBirth(LocalDate.of(1972, Month.NOVEMBER, 10))
                .married(true)
                .hasKids(false)
                .gender(Gender.FEMALE)
                .patience(maryJohnson)
                .build();

        ContractInformation maryJohnsonContract = ContractInformation.builder()
                .address("789 Maple Avenue, Villagetown, State")
                .phoneNumber("(555) 555-5555")
                .email("mary.johnson@example.com")
                .patience(maryJohnson)
                .build();

        InsuranceInformation maryJohnsonInsurance = InsuranceInformation.builder()
                .insuranceProvider("DEF Health")
                .insurancePolicyNumber(2468109753L)
                .coverageDateStart(LocalDate.of(2008, Month.JUNE, 1))
                .coverageDateEnd(LocalDate.of(2023, Month.MAY, 31))
                .patience(maryJohnson)
                .build();

        MedicalHistory maryJohnsonMedicalHistory = MedicalHistory.builder()
                .conditions(Collections.emptyList())
                .allergies(List.of("Shellfish"))
                .medications(Collections.emptyList())
                .surgeries(Collections.emptyList())
                .patience(maryJohnson)
                .build();

        maryJohnson.setDemographicInformation(maryJohnsonDemographic);
        maryJohnson.setContractInformation(maryJohnsonContract);
        maryJohnson.setInsuranceInformation(maryJohnsonInsurance);
        maryJohnson.setMedicalHistory(maryJohnsonMedicalHistory);
        maryJohnson.setDoctorIds(List.of(3L));

        Patience robertMiller = Patience.builder()
                .patienceId(generatePatienceId())
                .build();

        DemographicInformation robertMillerDemographic = DemographicInformation.builder()
                .name("Robert Miller")
                .age(34)
                .timeOfBirth(LocalDate.of(1990, Month.FEBRUARY, 28))
                .married(false)
                .hasKids(false)
                .gender(Gender.MALE)
                .patience(robertMiller)
                .build();

        ContractInformation robertMillerContract = ContractInformation.builder()
                .address("987 Pine Street, Hamletville, State")
                .phoneNumber("(555) 222-3333")
                .email("robert.miller@example.com")
                .patience(robertMiller)
                .build();

        InsuranceInformation robertMillerInsurance = InsuranceInformation.builder()
                .insuranceProvider("GHI Assurance")
                .insurancePolicyNumber(1357924680L)
                .coverageDateStart(LocalDate.of(2010, Month.AUGUST, 1))
                .coverageDateEnd(LocalDate.of(2025, Month.JULY, 31))
                .patience(robertMiller)
                .build();

        MedicalHistory robertMillerMedicalHistory = MedicalHistory.builder()
                .conditions(List.of("High Cholesterol"))
                .allergies(Collections.emptyList())
                .medications(Collections.emptyList())
                .surgeries(List.of("Appendectomy"))
                .patience(robertMiller)
                .build();

        robertMiller.setDemographicInformation(robertMillerDemographic);
        robertMiller.setContractInformation(robertMillerContract);
        robertMiller.setInsuranceInformation(robertMillerInsurance);
        robertMiller.setMedicalHistory(robertMillerMedicalHistory);
        robertMiller.setDoctorIds(List.of(4L));

        Patience emilyBrown = Patience.builder()
                .patienceId(generatePatienceId())
                .build();

        DemographicInformation emilyBrownDemographic = DemographicInformation.builder()
                .name("Emily Brown")
                .age(39)
                .timeOfBirth(LocalDate.of(1985, Month.SEPTEMBER, 5))
                .married(true)
                .hasKids(false)
                .gender(Gender.FEMALE)
                .patience(emilyBrown)
                .build();

        ContractInformation emilyBrownContract = ContractInformation.builder()
                .address("321 Elm Street, Riverside, State")
                .phoneNumber("(555) 444-5555")
                .email("emily.brown@example.com")
                .patience(emilyBrown)
                .build();

        InsuranceInformation emilyBrownInsurance = InsuranceInformation.builder()
                .insuranceProvider("JKL Health")
                .insurancePolicyNumber(9753108642L)
                .coverageDateStart(LocalDate.of(2003, Month.APRIL, 1))
                .coverageDateEnd(LocalDate.of(2018, Month.MARCH, 31))
                .patience(emilyBrown)
                .build();

        MedicalHistory emilyBrownMedicalHistory = MedicalHistory.builder()
                .conditions(Collections.emptyList())
                .allergies(Collections.emptyList())
                .medications(List.of("Painkillers", "Steroids"))
                .surgeries(Collections.emptyList())
                .patience(emilyBrown)
                .build();

        emilyBrown.setDemographicInformation(emilyBrownDemographic);
        emilyBrown.setContractInformation(emilyBrownContract);
        emilyBrown.setInsuranceInformation(emilyBrownInsurance);
        emilyBrown.setMedicalHistory(emilyBrownMedicalHistory);
        emilyBrown.setDoctorIds(List.of(5L));

        return List.of(johnDoe, janeSmith, maryJohnson, robertMiller, emilyBrown);
    }

    public static PatienceRequest getPatienceReqMock() {
        Patience patience = getAllPatients().get(0);
        return mapToPatienceReq(patience);
    }

    private static String generatePatienceId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    private static PatienceRequest mapToPatienceReq(Patience patience) {
        ContractInformationDTO contractInformationDTO = ContractInformationMapping.INSTANCE.convertToDto(patience.getContractInformation());
        DemographicInformationDTO demographicInformationDTO = DemographicInformationMapping.INSTANCE.convertToDto(patience.getDemographicInformation());
        InsuranceInformationDTO insuranceInformationDTO = InsuranceInformationMapping.INSTANCE.convertToDto(patience.getInsuranceInformation());
        MedicalHistoryDTO medicalHistoryDTO = MedicalHistoryMapping.INSTANCE.convertToDto(patience.getMedicalHistory());
        return new PatienceRequest(contractInformationDTO, demographicInformationDTO, insuranceInformationDTO, medicalHistoryDTO, List.of("John"));
    }

    private static Patience mapPatienceEntity(PatienceRequest request) {
        Patience patience = Patience.builder()
                .patienceId(generatePatienceId())
                .build();
        ContractInformation contractInformation = ContractInformationMapping.INSTANCE.convertToEntity(request.contractInformationRequest());
        contractInformation.setPatience(patience);

        DemographicInformation demographicInformation = DemographicInformationMapping.INSTANCE.convertToEntity(request.demographicInformationRequest());
        demographicInformation.setPatience(patience);

        InsuranceInformation insuranceInformation = InsuranceInformationMapping.INSTANCE.convertToEntity(request.insuranceInformationRequest());
        insuranceInformation.setPatience(patience);

        MedicalHistory medicalHistory = MedicalHistoryMapping.INSTANCE.convertToEntity(request.medicalHistoryRequest());
        medicalHistory.setPatience(patience);

        patience.setContractInformation(contractInformation);
        patience.setDemographicInformation(demographicInformation);
        patience.setInsuranceInformation(insuranceInformation);
        patience.setMedicalHistory(medicalHistory);

        return patience;
    }
}
