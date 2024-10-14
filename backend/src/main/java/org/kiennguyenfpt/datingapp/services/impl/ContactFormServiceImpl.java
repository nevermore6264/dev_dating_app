package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.entities.ContactForm;
import org.kiennguyenfpt.datingapp.repositories.ContactFormRepository;
import org.kiennguyenfpt.datingapp.services.ContactFormService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactFormServiceImpl implements ContactFormService {
    private final ContactFormRepository contactFormRepository;

    public ContactFormServiceImpl(ContactFormRepository contactFormRepository) {
        this.contactFormRepository = contactFormRepository;
    }

    @Override
    public List<ContactForm> getAllContactForms() {
        // Fetch all contact forms from the database
        return contactFormRepository.findAll();
    }

    @Override
    public int replyToContactForm(Long id, String replyMessage) {
        // Fetch contact form by ID
        Optional<ContactForm> contactFormOpt = contactFormRepository.findById(id);
        if (contactFormOpt.isPresent()) {
            ContactForm contactForm = contactFormOpt.get();

//            // Update the contact form's reply details
//            contactForm.setReplyMessage(replyMessage);  // Assuming you have a replyMessage field
//            contactForm.setReplyDate(LocalDate.now());  // Set current date as reply date
//            contactForm.setStatus(true); // Mark the form as responded

            // Save the updated contact form back to the database
            contactFormRepository.save(contactForm);
            return 1; // Success
        }
        return 0; // Failure (contact form not found)
    }
}
