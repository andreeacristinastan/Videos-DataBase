Nume si grupa : Stan Andreea-Cristina 323CA

Pentru a-mi crea propria baza de date, am ales sa pastrez aceeasi structura ca si cea din schelet si sa copiez, rand pe rand, fiecare informatie din Inputul format in scheletul de cod.

Pentru o structurare cat mai concisa si pentru a fi cat mai usor de vizualizat intreaga rezolvare a temei, am ales sa-mi impart operatiile in pachete cat mai bine sortate:

In pachetul init am ales sa includ toate clasele care au ajutat la crearea propriei mele baza de date. Acestea sunt numite *Init(ex: ActorInit) in functie de ce initializez in fiecare.
Tot aici am ales sa includ si clasa in care creez noi structuri de date pentru a copia tot ce se afla in fiecare clasa de *Init din inputul oferit in schelet.
Suplimentar, pentru cateva dintre clasele ce formeaza baza de date, am ales sa adaug campuri noi pentru a usura rezolvarea cerintelor si pentru a retine mai usor informatia de care am avut nevoie.
Atstfel:
	In clasa MovieInit am ales sa adaug un dictionar in care retin fiecare utilizator si nota pe care a oferit-o filmului curent.
	In clasa de UserInit am ales sa adaug un dictionar in care sa memorez numele fiecarui serial si carui sezon a acordat o nota utilizatorul curent.
	Tot aici, am ales sa adaug si un arrayList in care sa retin toate notele pe care le ofera un user.
Clasa DataBase creeaza efectiv baza mea de date cu listele aferente.

In pachetul databasecommands am inclusa toata logica comenzilor ce trebuie efectuate in cadrul temei si clasele aferente.

Am ales sa-mi caut intr-o clasa separata ce tip de comanda se doreste a fi efectuata, apoi sa apelez metoda corespunzatoare si sa returnez rezultatul obtinut(Command).
Pentru fiecare clasa de comenzi, am ales sa includ metode care cauta userul pentru care se va efectua comanda, si apoi, in functie de cerinta sa afisez mesajul potrivit.
In anumite clase din cadrul acestui pachet am inclus si niste metode ajutatoare(exemplu in clasa Rating am introdus metoda checkRating ce verifica daca userul a mai dat inainte o nota sezonului din serialul cautat si intoarce 0 sau 1(True or False).

In pachetul databasequeries am inclus toata logica pentru realizarea queiury-urilor astfel :
Asemanator ca si la comenzi, stabilesc intr-o clasa separata(Query) ce clasa va fi apelata pentru rezolvare.
	Am format pachete separate pentru fiecare tip de query in parte(pentru actori, useri si videouri) si am creat si un pachet sort in care am introdus clasa ce se ocupa cu metode de sortare in functie de criterii pentru fiecare query in parte.
In fiecare clasa din fiecare pachet din query, am ales ca pe parcursul cautarilor(ex: cautarea primelor N videouri sortate după numarul de aparitii în listele de video-uri favorite ale utilizatorilor) sa introduc fiecare noua pereche cheie-valoare(ex: numele videoului-numarul de aparitii) intr-un Map format cu un LinkedHashMap pentru a pastra ordinea de adaugare a elementelor in cadrul sau si mai apoi sa sortez acest dictionar prin intermediul unei liste ajutatoare folosindu-ma de stream-ul din java.

In pachetul databaserecommendations este cuprinsa tota logica pentru realizarea recomandarilor pentru un anumit utilizator.
Pentru o structurare cat mai clara, am impartit tipurile de recomandari in pachete clasate in functie de tipul de abonare al userului(BASIC sau PREMIUM) si am inlcus acolo clasele pentru fiecare tip de recomandare in parte.
Asemanator cu queries-urile, sortarea am ales sa o fac tot cu ajutorul unei liste intermediare in care am plasat Map-ul format tot cu un LinkedHashMap pentru a pastra ordinea de adaugare, folosindu-ma tot de stream-ul din java.


Intreaga tema se foloseste de .get pentru a obtine informatia stocata in baza de date si pentru a o actualiza. De asemenea, pentru a scrie in cadrul fisierului JSON am ales sa returnez succesiv mesajele necesare din fiecare clasa in parte si sa efectuez acest lucru in cadrul Mainului.

