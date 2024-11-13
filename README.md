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


`AssuranceInformationAs400ServiceImpl` sınıfında AS400 sistemine gönderilen veriler, `updateAgencyWarrantyOnAs400` metodu ile hazırlanır ve `callProgram` metodu ile AS400 sistemine iletilir. Bu veriler iki ana kategoride toplanır: `AssuranceInformationAs400Request` ve `AssuranceAddressInformationAs400Request`.

### `AssuranceInformationAs400Request` Verileri
- **agencyCode**: Ajans kodu
- **subAgencyCode**: Alt ajans kodu
- **assuranceType**: Teminat türü
- **assuranceStatus**: Teminat durumu
- **amount**: Teminat tutarı
- **currency**: Para birimi
- **createDate**: Oluşturulma tarihi
- **expireDate**: Bitiş tarihi
- **sequenceNumber**: Sıra numarası
- **mapSection**: Harita bölümü
- **map**: Harita
- **parcel**: Parsel
- **hypothecDegree**: İpotek derecesi
- **expertiseDate**: Ekspertiz tarihi
- **expertiseAmount**: Ekspertiz tutarı
- **firePolicyEndDate**: Yangın poliçesi bitiş tarihi
- **bankName**: Banka adı
- **bankBranchName**: Banka şube adı
- **fixedTermStartDate**: Sabit vadeli başlangıç tarihi
- **fixedTermEndDate**: Sabit vadeli bitiş tarihi
- **documentNumber**: Belge numarası
- **wageNumber**: Ücret numarası
- **documentDate**: Belge tarihi
- **pageNumber**: Sayfa numarası

### `AssuranceAddressInformationAs400Request` Verileri
- **realEstateQualification**: Gayrimenkul niteliği
- **block**: Blok
- **provinceCode**: İl kodu
- **provinceName**: İl adı
- **subProvinceCode**: İlçe kodu
- **subProvinceName**: İlçe adı
- **flatNumber**: Daire numarası
- **street**: Sokak
- **neighborhood**: Mahalle
- **daskEndDate**: DASK bitiş tarihi
- **sectionNumber**: Bölüm numarası

Bu veriler, `callProgram` metodunda `ProgramCallDocument` sınıfı kullanılarak AS400 sistemine gönderilir. Veriler, `prepareWidiziParam` ve `prepareWidiziaParam` metodları ile hazırlanır ve AS400 programına parametre olarak iletilir.
