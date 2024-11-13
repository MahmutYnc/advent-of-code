# advent-of-code-2023

Welcome to the Advent of Code[^aoc] Kotlin project created by [mahmutync][github] using the [Advent of Code Kotlin Template][template] delivered by JetBrains.

In this repository, mahmutync is about to provide solutions for the puzzles using [Kotlin][kotlin] language.

If you're stuck with Kotlin-specific questions or anything related to this template, check out the following resources:

- [Kotlin docs][docs]
- [Kotlin Slack][slack]
- Template [issue tracker][issues]


[^aoc]:
    [Advent of Code][aoc] – An annual event of Christmas-oriented programming challenges started December 2015.
    Every year since then, beginning on the first day of December, a programming puzzle is published every day for twenty-five days.
    You can solve the puzzle and provide an answer using the language of your choice.

[aoc]: https://adventofcode.com
[docs]: https://kotlinlang.org/docs/home.html
[github]: https://github.com/mahmutync
[issues]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template/issues
[kotlin]: https://kotlinlang.org
[slack]: https://surveys.jetbrains.com/s3/kotlin-slack-sign-up
[template]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template


Veriler, `AssuranceInformationAs400ServiceImpl` sınıfında `updateAgencyWarrantyOnAs400` metodu ile hazırlanır. Bu metod, `AssuranceInformation` nesnelerini alır ve bunları `AssuranceInformationAs400Request` ve `AssuranceAddressInformationAs400Request` nesnelerine dönüştürür. İşte bu dönüşümün nasıl yapıldığı:

1. **AssuranceInformationAs400Request Hazırlama:**
   - `prepareAssuranceInformationAs400Request` metodu, `AssuranceInformation` nesnesinden gerekli bilgileri alarak `AssuranceInformationAs400Request` nesnesini oluşturur.
   - Bu metod, ajans kodu, teminat türü, teminat durumu, tutar, para birimi, oluşturulma tarihi, bitiş tarihi, sıra numarası, harita bilgileri, ipotek derecesi, ekspertiz tarihi ve tutarı, banka bilgileri gibi verileri hazırlar.

2. **AssuranceAddressInformationAs400Request Hazırlama:**
   - `prepareAssuranceAddressInformationAs400Request` metodu, `AssuranceInformation` nesnesinden gerekli bilgileri alarak `AssuranceAddressInformationAs400Request` nesnesini oluşturur.
   - Bu metod, gayrimenkul niteliği, blok, il ve ilçe kodları ve adları, daire numarası, sokak, mahalle, DASK bitiş tarihi gibi verileri hazırlar.

3. **Verilerin AS400 Sistemine Gönderilmesi:**
   - Hazırlanan `AssuranceInformationAs400Request` ve `AssuranceAddressInformationAs400Request` nesneleri, `UpdateAgencyWarrantyRequest` nesnesine eklenir.
   - `callProgram` metodu, bu verileri AS400 sistemine gönderir.

`UpdateAgencyWarrantyRequest` sınıfı, AS400 sistemine gönderilecek verileri kapsüllemek için kullanılır. İki ana alan içerir:

1. `assuranceInformationRequestList`: `AssuranceInformationAs400Request` nesnelerinin bir listesidir. Bu nesneler, ajans kodu, teminat türü, tutar ve diğer ilgili detaylar gibi teminatla ilgili ayrıntılı bilgileri içerir.

2. `assuranceAddressInformationRequestList`: `AssuranceAddressInformationAs400Request` nesnelerinin bir listesidir. Bu nesneler, gayrimenkul niteliği, blok, il kodu ve diğer adres detayları gibi teminatın adresle ilgili bilgilerini içerir.

### `UpdateAgencyWarrantyRequest` Alanları
- **assuranceInformationRequestList**: `AssuranceInformationAs400Request` nesnelerinin listesi.
  - **agencyCode**: Ajans kodu.
  - **subAgencyCode**: Alt ajans kodu.
  - **assuranceType**: Teminat türü.
  - **assuranceStatus**: Teminat durumu.
  - **amount**: Teminat tutarı.
  - **currency**: Para birimi.
  - **createDate**: Oluşturulma tarihi.
  - **expireDate**: Bitiş tarihi.
  - **sequenceNumber**: Sıra numarası.
  - **mapSection**: Harita bölümü.
  - **map**: Harita.
  - **parcel**: Parsel.
  - **hypothecDegree**: İpotek derecesi.
  - **expertiseDate**: Ekspertiz tarihi.
  - **expertiseAmount**: Ekspertiz tutarı.
  - **firePolicyEndDate**: Yangın poliçesi bitiş tarihi.
  - **bankName**: Banka adı.
  - **bankBranchName**: Banka şube adı.
  - **fixedTermStartDate**: Sabit vadeli başlangıç tarihi.
  - **fixedTermEndDate**: Sabit vadeli bitiş tarihi.
  - **documentNumber**: Belge numarası.
  - **wageNumber**: Ücret numarası.
  - **documentDate**: Belge tarihi.
  - **pageNumber**: Sayfa numarası.

- **assuranceAddressInformationRequestList**: `AssuranceAddressInformationAs400Request` nesnelerinin listesi.
  - **realEstateQualification**: Gayrimenkul niteliği.
  - **block**: Blok numarası.
  - **provinceCode**: İl kodu.
  - **provinceName**: İl adı.
  - **subProvinceCode**: İlçe kodu.
  - **subProvinceName**: İlçe adı.
  - **flatNumber**: Daire numarası.
  - **street**: Sokak adı.
  - **neighborhood**: Mahalle adı.
  - **daskEndDate**: DASK bitiş tarihi.
  - **sectionNumber**: Bölüm numarası.

```java
private AssuranceInformationAs400Request prepareAssuranceInformationAs400Request(Long agencyCode, AssuranceInformation item, String currency, String sequenceNumber) {
    return AssuranceInformationAs400Request.builder()
        .agencyCode(agencyCode.toString())
        .subAgencyCode("0000")
        .assuranceType(item.getAssuranceType().getKey())
        .assuranceStatus(item.getAssuranceStatus().getKey())
        .amount(item.getAmount())
        .currency(currency)
        .createDate(Objects.nonNull(item.getCreatedDate()) ? getAs400DateFormat(item.getCreatedDate().toLocalDate()) : "0")
        .expireDate(Objects.nonNull(item.getExpireDate()) ? getAs400DateFormat(item.getExpireDate()) : "0")
        .sequenceNumber(sequenceNumber)
        .mapSection(Objects.nonNull(item.getMapSection()) ? item.getMapSection().trim() : "0")
        .map(Objects.nonNull(item.getMap()) ? item.getMap().trim() : "0")
        .parcel(Objects.nonNull(item.getParcel()) ? item.getParcel().trim() : "0")
        .hypothecDegree(Objects.nonNull(item.getHypothecDegree()) ? item.getHypothecDegree().trim() : "0")
        .expertiseDate(getAs400DateFormat(item.getExpertiseDate()))
        .expertiseAmount(Objects.nonNull(item.getExpertiseAmount()) ? item.getExpertiseAmount() : BigDecimal.ZERO)
        .firePolicyEndDate(getAs400DateFormat(item.getFirePolicyEndDate()))
        .bankName(Objects.nonNull(item.getBankName()) ? item.getBankName().substring(0, Math.min(item.getBankName().length(), 50)).trim() : "0")
        .bankBranchName(Objects.nonNull(item.getBankBranchName()) ? item.getBankBranchName().substring(0, Math.min(item.getBankBranchName().length(), 50)).trim() : "0")
        .fixedTermStartDate(getAs400DateFormat(item.getFixedTermStartDate()))
        .fixedTermEndDate(getAs400DateFormat(item.getFixedTermEndDate()))
        .documentNumber(Objects.nonNull(item.getDocumentNumber()) ? item.getDocumentNumber().substring(0, Math.min(item.getDocumentNumber().length(), 16)).trim() : "0")
        .wageNumber(Objects.nonNull(item.getWageNumber()) ? item.getWageNumber().trim() : "0")
        .documentDate(getAs400DateFormat(item.getDocumentDate()))
        .pageNumber(Objects.nonNull(item.getPageNumber()) ? item.getPageNumber().trim() : "0")
        .build();
}

private AssuranceAddressInformationAs400Request prepareAssuranceAddressInformationAs400Request(AssuranceInformation item) {
    return AssuranceAddressInformationAs400Request.builder()
        .realEstateQualification(Objects.nonNull(item.getRealEstateQualification()) ? item.getRealEstateQualification().getKey() : "0")
        .block(Objects.nonNull(item.getBlock()) ? item.getBlock().trim() : "0")
        .provinceCode(Objects.nonNull(item.getProvinceCode()) ? item.getProvinceCode().trim() : "0")
        .provinceName(Objects.nonNull(item.getProvinceName()) ? item.getProvinceName().trim() : "0")
        .subProvinceName(Objects.nonNull(item.getSubprovinceName()) ? item.getSubprovinceName().trim() : "0")
        .subProvinceCode(Objects.nonNull(item.getSubprovinceCode()) ? item.getSubprovinceCode().trim() : "0")
        .flatNumber(Objects.nonNull(item.getFlatNumber()) ? item.getFlatNumber().trim() : "0")
        .street(Objects.nonNull(item.getStreetName()) ? item.getStreetName().trim() : "0")
        .neighborhood(Objects.nonNull(item.getNeighborhoodName()) ? item.getNeighborhoodName().trim() : "0")
        .daskEndDate(getAs400DateFormat(item.getDaskEndDate()))
        .sectionNumber(Objects.nonNull(item.getSectionNumber()) ? item.getSectionNumber() : "0")
        .build();
}
```
