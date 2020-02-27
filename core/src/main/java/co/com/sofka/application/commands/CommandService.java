package co.com.sofka.application.commands;


import co.com.sofka.business.generic.UseCase;
import co.com.sofka.infraestructure.FirestoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Deprecated
public class CommandService {
    private CommandBuilder commandBuilder;
    private FirestoreRepository firestoreRepository;

    @Autowired
    public CommandService(CommandBuilder commandBuilder, FirestoreRepository firestoreRepository) {
        this.commandBuilder = commandBuilder;
        this.firestoreRepository = firestoreRepository;
    }

    public void executor(IssueCommand command) {
        UseCase useCase = commandBuilder.useCases().get(command.type);

    }
}
