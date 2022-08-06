AndGro
======

Android reader til Gyldendals "download"-ordbøger

Dette projekt er en videreførsel af Athas-crew'ets arbejde med at lave en reader af Gyldendals ordbøger, der fungerer på andet end Windows og Mac.

Denne reader gør, at Gyldendals "Download" ordbøger (kan købes her: http://ordbog.gyldendal.dk/) nu også kan bruges på Android enheder.

I skrivende stund findes der ordbøger engelsk, tysk, fransk, spansk, italiensk, svensk, norsk, engelsk (fag/teknik), stor dansk-engelsk, stor engelsk-dansk, retskrivning, fremmedord, synonymer, dansk. Alle disse kan håndteres af dette projekt.

For at køre programmet, skal du

(1) Installere apk-filen på din enhed (https://github.com/ejvindh/AndGro/releases).
(2) Derudover skal du kopiere dine ordbogs-filer over på enheden. For at finde disse filer skal du fra din computer-installation gå ind i /data/-mappen. Her ska du bruge filerne med fil-endelserne *.dat og *.gdd (i Windows findes de i mappen %Program Files%Gyldendal/Røde Ordbøger/data).
(3) Så er du klar til at køre applikationen på din enhed. Første gang du kører det, skal du sandsynligvis fortælle applikationen, hvor du har gemt ordbogs-filerne. Dette gør du vha. menu-knappen.

Da jeg ikke har lagt den ud på Google-play*, forudsætter installation, at man slår “Ukendte kilder” til inde i Indstillinger-Sikkerhed. Der skal altså være flueben, således at man accepterer at installere apps fra ukendte kilder. For mindre kyndige gælder naturligvis at dette bør man kun gøre i forbindelse med at man installerer apps fra kilder, men stoler fuldstændigt på. Og hvis du i undtagelsestilfælde sætter fluebenet, så husk at fjerne det straks du er færdig med at installere den ønskede app smile

* (den kommer ikke på Google Play, da jeg principielt er modstander af at skulle betale Google for at hjælpe med at forbedre deres styresystem)

God arbejdslyst. Ligesom Athas vil jeg også gerne understrege, at det er strengt ulovligt at piratkopiere ordbogen. Hvis du kan lide sproget, så køb det! Dette program er udelukkende tænkt til at hjælpe folk til at kunne migrere til andre styresystemer end Windows og Mac -- uden at skulle miste adgangen til deres legalt erhvervede software.

//ejvindh

### Forgængere til dette projekt:

Java-port: https://github.com/ejvindh/JavaGro

Min videreudvikling (læser flere ordbøger) (Python): https://github.com/ejvindh/spt-gro

Den oprindelige reader, lavet af Athas-crew'et (Python): https://github.com/Athas/spt-gro


Udviklings-linie:

v1.3: August 2022:
- Bugfix: Indført ordentligt layout for Landscape-devices

v1.2: Marts 2019:
- Bugfix: Havde glemt at 'ä' som internationalt tegn
- Bedre håndtering af permissions (for at tage højde for Android 6.0+) 

v1.1: November 2014:
- Bugfix: Bedre søgning på internationale bogstaver
- Bugfix: Indføre manglende mellemrum i visse opslag i franske ordbøger
- Fixer mere glidende overgang mellem horisontal/vertikal visning
- Fjerner toolbar for zoom (man kan stadig pinch'n-zoom)

v1.0: Juni/juli 2013
- Første brugbare udgave
