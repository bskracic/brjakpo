package hr.bskracic.bookexchangeplatform.config;

import hr.bskracic.bookexchangeplatform.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor

public class SchedulerExample implements ApplicationRunner {

    private BookService bookService;
    final static Logger logger = LoggerFactory.getLogger(SchedulerExample.class);

    @Scheduled(fixedRate = 1000 * 60)
    public void loggerForNumberOfTransaction() {
        logger.info("amount of books active: " + bookService.getAllBookAds().size());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("scheduled");
    }
}
