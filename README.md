```markdown
# Aplikacja NN Bank Account

## Wymagania wstępne

- **Java 21**: Upewnij się, że zainstalowano Java JDK 21.
- **Maven**: Upewnij się, że Maven jest zainstalowany.
- **Docker**: Upewnij się, że Docker jest zainstalowany.

## Uruchomienie aplikacji

Aby uruchomić aplikację, wykonaj następujące polecenia:

```bash
chmod +x start_project.sh
./start_project.sh
```

## Ulepszenia przed wdrożeniem na produkcję

Poniżej znajdują się sugestie dotyczące ulepszeń, które warto rozważyć przed wdrożeniem aplikacji na środowisko
produkcyjne:

1. **Zmiana bazy danych**:
    - Zmiana bazy danych z H2 na bardziej odpowiednie dla produkcji rozwiązanie, takie jak PostgreSQL, MySQL lub inna
      wybrana baza danych.

2. **Zabezpieczenia aplikacji**:
    - Zaimplementowanie mechanizmy bezpieczeństwa, takie jak Spring Security, do ochrony aplikacji przed
      nieautoryzowanym dostępem.

3. **Bezpieczne przechowywanie danych uwierzytelniających**:
    - Nieprzechowywanie danych uwierzytelniających do bazy danych w plikach `properties`. Użyj bezpiecznych metod,
      takich jak zmienne środowiskowe lub zewnętrzne narzędzia do zarządzania tajemnicami (np. Vault).

4. **Event Sourcing**:
    - Zimplementowanie mechanizmu event sourcing do zarządzania i przechowywania stanu encji `BankAccount`, aby śledzić
      zmiany w czasie.

5. **Mechanizm cache**:
    - Rozważenie zaimplementowania mechanizmu pamięci podręcznej (cache) do przechowywania aktualnych danych kursów
      walutowych, aby zoptymalizować wydajność.

6. **HATEOAS**:
    - Dodanie HATEOAS links do zasobów RESTful, aby API było bardziej odkrywcze i łatwiejsze w nawigacji dla klientów.