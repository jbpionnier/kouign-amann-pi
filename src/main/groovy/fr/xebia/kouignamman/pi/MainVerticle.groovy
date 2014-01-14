package fr.xebia.kouignamman.pi

import fr.xebia.kouignamman.pi.db.PersistenceVerticle
import fr.xebia.kouignamman.pi.hardwareTest.TestLcd
import fr.xebia.kouignamman.pi.hardwareTest.TestLedBackPack
import fr.xebia.kouignamman.pi.vote.DataManagementVerticle
import fr.xebia.kouignamman.pi.vote.NfcVerticle
import fr.xebia.kouignamman.pi.vote.VoteVerticle
import org.vertx.groovy.platform.Verticle
import org.vertx.java.core.logging.Logger

class MainVerticle extends Verticle {
    Logger logger

    def start() {
        logger = container.logger
        logger.info "Starting"

        container.deployWorkerVerticle('groovy:' + VoteVerticle.class.name, container.config, 1)
        container.deployWorkerVerticle('groovy:' + NfcVerticle.class.name, container.config, 1)
        container.deployWorkerVerticle('groovy:' + DataManagementVerticle.class.name, container.config, 1)
        // Local persistence
        container.deployWorkerVerticle('groovy:' + PersistenceVerticle.class.name, container.config, 1)

        if (container.config.testLcd) {
            container.deployWorkerVerticle('groovy:' + TestLcd.class.name, container.config, 1)
        }
        if (container.config.testLedBackPack) {
            container.deployWorkerVerticle('groovy:' + TestLedBackPack.class.name, container.config, 1)
        }


    }
}
