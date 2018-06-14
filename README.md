# bashscraper
Scala project scraping bash.org.pl/latest pages

## Installation
```
git clone https://github.com/kelsohmm/bashscraper.git
cd bashscraper
sbt
sbt:bashscraper> compile
sbt:bashscraper> run
```

## Configuration
Scraper uses typesafe config file default.config. Parameters are:
- **outputFilepath** - path to file where scraped posts will be written
- **startPage** - page number to start scraping at
- **endPage** - page number to stop scraping
