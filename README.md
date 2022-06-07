# Elevator System

<h3>Instructions to use</h3>
<ul>
    <li>In elevator_system folder run './gradlew bootRun' command</li>
    <li>In elevators_app folder run 'npm start' command</li>
</ul>

<h4>System requirements:</h4>
<ul>
    <li>Node.js</li>
    <li>Java 18</li>
</ul>

<h3>Aplikacja obsługująca system wind.</h3>
<h4>Główne założenia systemu:</h4>
<ul>
    <li>system obsługuje następujące metody:
        <ul>
            <li>'pickup' - zgłoszenie przywołania windy na dane piętro</li>
            <li>'target' - zgłoszenie wyboru piętra dla windy o danym id</li>
            <li>'update' - metody do zmiany pozycji oraz stanu wybranej windy</li>
            <li>'step' - wykonanie kroku symulacji</li>
            <li>'status' - zwrócenie statusu systemu wind</li>
        </ul>
    </li>
    <li>Wybór piętra na które zmierza winda jest dokonywany na podstawie priorytetów</li>
    <li>Im dużej zgłoszenie czeka na wykonanie tym większy ma priorytet</li>
    <li>Bardzej oddalone zapytania mają mniejszy priorytet</li>
    <li>Zależność priorytetów 'pickup' oraz 'target' są 
        modyfikowalne (domyślnie 'target' ma większy priorytet)</li>
    <li>Winda która jest w ruch nie może nagle zmienić kierunku</li>
    <li>Jeśli winda po drodze do swojego celu napotka piętro z 
        którego ktoś chce wsiąść/wysiąść, zatrzymuje się na nim</li>
</ul>

<h4>Funkcjonalności:</h4>
<h6>System wind jest dostępny poprzez aplikacje minimalistyczny 
    frontend napisany w Reakcie, </br> 
    oraz znacznie bardziej konfigurowalne 
    REST api dostępne pod endpointem '/api/system', </br>
    dokumentacja całego 
    api jest wygenerowana automatycznie przez 'springdoc' pod </br>
    endpointem '/swagger-ui/index.html'</h6>

<h4>Dodatkowe informacje:</h4>
<h6>Backend był tworzony z użyciem Spring'a.</h6>
<h6>Cały kod systemu wind jest udokumentowy w 
    postaci JavaDoc.</h6>
<h6>Testy które wykonałem były głównie maualne oraz integracyjne.</h6>
<h6>W folderze postman_requests są przykładowe zaptania do api.</h6>
<h6>System może nie działać poprawnie przy używaniu go asynchronicznie, </br>
ponieważ nie był tworzony z myślą o wielowątkowości.</h6>