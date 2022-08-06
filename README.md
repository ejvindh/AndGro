AndGro
======

Android reader til Gyldendals "download"-ordb�ger

Dette projekt er en videref�rsel af Athas-crew'ets arbejde med at lave en reader af Gyldendals ordb�ger, der fungerer p� andet end Windows og Mac.

Denne reader g�r, at Gyldendals "Download" ordb�ger (kan k�bes her: http://ordbog.gyldendal.dk/) nu ogs� kan bruges p� Android enheder.

I skrivende stund findes der ordb�ger engelsk, tysk, fransk, spansk, italiensk, svensk, norsk, engelsk (fag/teknik), stor dansk-engelsk, stor engelsk-dansk, retskrivning, fremmedord, synonymer, dansk. Alle disse kan h�ndteres af dette projekt.

For at k�re programmet, skal du

(1) Installere apk-filen p� din enhed (https://github.com/ejvindh/AndGro/releases).
(2) Derudover skal du kopiere dine ordbogs-filer over p� enheden. For at finde disse filer skal du fra din computer-installation g� ind i /data/-mappen. Her ska du bruge filerne med fil-endelserne *.dat og *.gdd (i Windows findes de i mappen %Program Files%Gyldendal/R�de Ordb�ger/data).
(3) S� er du klar til at k�re applikationen p� din enhed. F�rste gang du k�rer det, skal du sandsynligvis fort�lle applikationen, hvor du har gemt ordbogs-filerne. Dette g�r du vha. menu-knappen.

Da jeg ikke har lagt den ud p� Google-play*, foruds�tter installation, at man sl�r �Ukendte kilder� til inde i Indstillinger-Sikkerhed. Der skal alts� v�re flueben, s�ledes at man accepterer at installere apps fra ukendte kilder. For mindre kyndige g�lder naturligvis at dette b�r man kun g�re i forbindelse med at man installerer apps fra kilder, men stoler fuldst�ndigt p�. Og hvis du i undtagelsestilf�lde s�tter fluebenet, s� husk at fjerne det straks du er f�rdig med at installere den �nskede app smile

* (den kommer ikke p� Google Play, da jeg principielt er modstander af at skulle betale Google for at hj�lpe med at forbedre deres styresystem)

God arbejdslyst. Ligesom Athas vil jeg ogs� gerne understrege, at det er strengt ulovligt at piratkopiere ordbogen. Hvis du kan lide sproget, s� k�b det! Dette program er udelukkende t�nkt til at hj�lpe folk til at kunne migrere til andre styresystemer end Windows og Mac -- uden at skulle miste adgangen til deres legalt erhvervede software.

//ejvindh

### Forg�ngere til dette projekt:

Java-port: https://github.com/ejvindh/JavaGro

Min videreudvikling (l�ser flere ordb�ger) (Python): https://github.com/ejvindh/spt-gro

Den oprindelige reader, lavet af Athas-crew'et (Python): https://github.com/Athas/spt-gro


Udviklings-linie:

v1.3: August 2022:
- Bugfix: Indf�rt ordentligt layout for Landscape-devices

v1.2: Marts 2019:
- Bugfix: Havde glemt at '�' som internationalt tegn
- Bedre h�ndtering af permissions (for at tage h�jde for Android 6.0+) 

v1.1: November 2014:
- Bugfix: Bedre s�gning p� internationale bogstaver
- Bugfix: Indf�re manglende mellemrum i visse opslag i franske ordb�ger
- Fixer mere glidende overgang mellem horisontal/vertikal visning
- Fjerner toolbar for zoom (man kan stadig pinch'n-zoom)

v1.0: Juni/juli 2013
- F�rste brugbare udgave
