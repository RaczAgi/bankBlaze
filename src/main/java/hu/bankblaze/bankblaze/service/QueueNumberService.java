package hu.bankblaze.bankblaze.service;

import hu.bankblaze.bankblaze.model.QueueNumber;
import hu.bankblaze.bankblaze.repo.QueueNumberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.UserTransaction;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QueueNumberService {

    @Autowired
    private QueueNumberRepository queueNumberRepository;


    public void deleteQueueNumberById(Long id) {
        try {
            queueNumberRepository.deleteById(id);
        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {

            throw new EntityNotFoundException("Nem található entitás az adott azonosítóval: " + id);
        }
    }

    public void deleteAllQueueNumbers() {
        try {
            queueNumberRepository.deleteAll();
        } catch (Exception e) {
            throw new RuntimeException("Nem sikerült az összes entitás törlése.", e);
        }
    }

    public QueueNumber getQueueNumber() {
        return queueNumberRepository.findFirstByOrderByIdDesc();
    }

    public QueueNumber getQueueNumberById(Long id) {
        return queueNumberRepository.findById(id).orElse(null);
    }

    public void addQueueNumber(QueueNumber newQueueNumber) {
        newQueueNumber.setArrivalTime();
        queueNumberRepository.save(newQueueNumber);
    }

    public void modifyName(String newName) {
        QueueNumber queueNumber = getQueueNumber();
        queueNumber.setName(newName);
        queueNumberRepository.save(queueNumber);
    }

    public void modifyNumber(int newNumber) {
        QueueNumber queueNumber = getQueueNumber();
        queueNumber.setNumber(newNumber);
        queueNumberRepository.save(queueNumber);
    }

    public void modifyToRetail(Boolean isToRetail) {
        QueueNumber queueNumber = getQueueNumber();
        queueNumber.setToRetail(isToRetail);
        queueNumberRepository.save(queueNumber);
    }

    public void modifyToCorporate(Boolean isToCorporate) {
        QueueNumber queueNumber = getQueueNumber();
        queueNumber.setToCorporate(isToCorporate);
        queueNumberRepository.save(queueNumber);
    }

    public void modifyToTeller(Boolean isToTeller) {
        QueueNumber queueNumber = getQueueNumber();
        queueNumber.setToTeller(isToTeller);
        queueNumberRepository.save(queueNumber);
    }

    public void modifyToPremium(Boolean isToPremium) {
        QueueNumber queueNumber = getQueueNumber();
        queueNumber.setToPremium(isToPremium);
        queueNumberRepository.save(queueNumber);
    }

    public void deleteQueueNumber() {
        QueueNumber queueNumber = getQueueNumber();
        queueNumberRepository.deleteById(queueNumber.getId());
    }

    public int countRetail() {
        return queueNumberRepository.countByActiveIsTrueAndToRetailIsTrue();
    }

    public int countCorporate() {
        return queueNumberRepository.countByActiveIsTrueAndToCorporateIsTrue();
    }

    public int countTeller() {
        return queueNumberRepository.countByActiveIsTrueAndToTellerIsTrue();
    }

    public int countPremium() {
        return queueNumberRepository.countByActiveIsTrueAndToPremiumIsTrue();
    }

    public int getCount() throws Exception {
        String number = String.valueOf(getQueueNumber().getNumber());
        switch (number.substring(0, 1)) {
            case "1" -> {
                return countRetail();
            }
            case "2" -> {
                return countCorporate();
            }
            case "3" -> {
                return countTeller();
            }
            case "9" -> {
                return countPremium();
            }
        }
        throw new Exception();
    }

    public QueueNumber getNextRetail() {
        return queueNumberRepository.findFirstByActiveTrueAndToRetailTrue();
    }

    public QueueNumber getNextCorporate() {
        return queueNumberRepository.findFirstByActiveTrueAndToCorporateTrue();
    }

    public QueueNumber getNextTeller() {

        return queueNumberRepository.findFirstByActiveTrueAndToTellerTrue();
    }

    public QueueNumber getNextPremium() {

        return queueNumberRepository.findFirstByActiveTrueAndToPremiumTrue();
    }

    public QueueNumber getSmallestNumber(List<QueueNumber> queueNumberList) {
        Long minIndex = queueNumberList.get(0).getId();
        for (int i = 1; i < queueNumberList.size(); i++) {
            if (minIndex > queueNumberList.get(i).getId()) {
                minIndex = queueNumberList.get(i).getId();
            }
        }
        return getQueueNumberById(minIndex);
    }

    public Duration calculateAverageWaitingTime() {
        List<QueueNumber> queueNumbers = queueNumberRepository.findAll();

        // Szűrés azokra az elemekre, ahol mind az arrivalTime, mind az employeeTime nem null
        List<QueueNumber> validQueueNumbers = queueNumbers.stream()
                .filter(q -> q.getArrivalTime() != null && q.getEmployeeTime() != null)
                .collect(Collectors.toList());

        // Számítás az összes várakozási időből
        Duration totalWaitingTime = Duration.ZERO;
        for (QueueNumber queueNumber : validQueueNumbers) {
            Duration waitingTime = Duration.between(queueNumber.getArrivalTime(), queueNumber.getEmployeeTime());
            totalWaitingTime = totalWaitingTime.plus(waitingTime);
        }

        // Átlagos várakozási idő kiszámítása
        int numberOfValidQueueNumbers = validQueueNumbers.size();
        if (numberOfValidQueueNumbers > 0) {
            return totalWaitingTime.dividedBy(numberOfValidQueueNumbers);
        } else {
            return Duration.ZERO; // Alapértelmezett érték, ha nincs érvényes várakozási idő
        }
    }

    public String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        long minutes = absSeconds / 60;

        return String.format("%d perc", minutes);
    }
}

