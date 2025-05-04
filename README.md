# Know Your Fan - FURIA eSports

O projeto **Know Your Fan** tem como objetivo criar uma solução interativa que colete informações sobre fãs de eSports, permitindo que clubes de eSports, como a FURIA, conheçam melhor seus seguidores. Isso inclui a coleta de dados pessoais, a vinculação com redes sociais, upload de documentos para validação de identidade e mais.

## Funcionalidades

- Coleta de dados básicos como **nome**, **CPF**, **endereço** e **interesses pessoais** (jogos, eventos, etc).
- Upload de **documentos** para validação da identidade do fã.
- Integração com **links de perfis em sites de e-sports** como Steam, Faceit, e outros.
- Validação de documentos e perfis de e-sports com **IA** (a ser implementado).
- **Recomendações personalizadas** de jogos baseadas nos interesses do fã, utilizando um microserviço Python.
- Interface para **administração** dos dados coletados, com opções de exportação de dados para **CSV**.
- **Autenticação e controle de validação** para administradores.

## Tecnologias Utilizadas

- **Frontend**: HTML, CSS, JavaScript (Vanilla), FormData, Fetch API
- **Backend**: Java com Spring Boot
- **Banco de Dados**: H2 (para MVP)
- **Microserviço de Recomendações**: Python com FastAPI
- **Validação de Documentos**: (Futura implementação) - Uso de **OCR** para validar os documentos enviados
- **Integração com e-sports**: A validação dos links será feita com IA (a ser implementado)
- **Integração com APIs de recomendação**: Recomendação de jogos com base em interesses usando microserviço FastAPI

---

## Como Executar o Projeto

### 1. **Pré-requisitos**

Certifique-se de ter as seguintes ferramentas instaladas:

- [Java 17+](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/)
- [Python 3.7+](https://www.python.org/)
- [FastAPI](https://fastapi.tiangolo.com/)
- [Uvicorn](https://www.uvicorn.org/)
- [IDE como IntelliJ ou Eclipse](https://www.jetbrains.com/idea/) para desenvolvimento

### 2. **Clone o repositório**

Clone o repositório para o seu ambiente local:

```bash
git clone https://github.com/IWMVI/KnowYourFan.git
cd KnowYourFan
```

# KnowYourFan - Diagrama UML

![Diagrama UML do Sistema](https://www.plantuml.com/plantuml/png/ZLFRQXin47tVhn1-wRgjq_PriKrece8Xj27zWa7IfWAjj6Z60HFwLVs4_bXrKQALroP-iT5SvfmvP_OQ2Hpj1zsyssmsxDQG4Y0aidkd35SZA6G-qN0DYEmx6FRSCBPYIdJipfh7-31sa1txHKxb01_xwYsDa4voMmML8Hz4b5Y5XUNxGHgozq2_9vaFx56Ixy1FcFxRPVEdoSHkdqB7HTm448Gy1_qDICQO3gzBiNO6kLCSJiBrOBeWKrexikeoknX20jhZcr39ZGSvMbJqxwzJDjaGDUk1ou2HqzOTs7ESr2iZlXvknhwT6B3isC-Hb3MWrxvf6oiHdcJxmdKPVprg6Pyfdq7uuJMziOP06G_G5eaZsfr2MgV-RJMqA7TchcUn4u7MBafQfhqsRb-r2VZEQYtTqO6MHFQddohLrS95S0u2a1mudvgAyY1nj0Pb-2Zei4uB9lsqGehrs6QEITkZWvnnaKUoThdmc95Ju5AchO_e0bgv4TkP5xCBdl5JSuUwKbwpJCMUdfjQzZ7l-F8-JxXSkGOUxdC8VSbDBoPRV5cmrOejFbzTlLyK4arQvsowgMx0bsnhotD_NJAteTAe59uKureQiH_qVm00)

<!--
# Caso de Uso (CSU)

![Caso de Uso](https://www.plantuml.com/plantuml/png/LP31IePG48NtynJ3lwg52zDr29OHBLePj8hXEiW5dGipLuEYfsdH4_G4lbWgDz3bcUzm-9ZA8ccSnW6V9Bo_XaaRahVMk2RZEq-zqWX0BWR5P-r9_0TrGTEbMRu1b5qawGV6os7ZWf-0U5LJHnQLzkurac5RvnjuO6LndXJlPvOOBC6R3JwAEUMP5NbCFE5Ymo_U9XfM4yMzwPUVvPV_5qe0FBfYbjscuTKXfVoKYbCguGkWOkdMj_m1) -->
<!--
# Arquitetura

![Arquitetura](https://www.plantuml.com/plantuml/png/TP0_JyCm4CLtVufJ9XWgzWweTVw80aDeiY24q66iACAvgyuN613V7TCI2G-TBFczd_LxtaH5qNaAS4Vy-SYpD4Wl9Ky79azQZJW8JiQuvGQAHfZKqri16A6fuSi0F6rfc0FArTwZqsVpdH4MtVi2s3-WEyX80rZcL0Vu_pvIoQJ28NWfygp-I0UVZNSKdOmEiqRkKg7YAqP_bAdiIQRbg8Fuxk7-g9v6hLRNlwfhkEdx5YxsTTV3jjrT6dlA4hc6sygMncpIjFI1Fm00) -->
