package com.jianli.JavaEmailDemo;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;

public class EmailAttachmentReceiver {
    private String saveDirectory;

    /**
     * Sets the directory where attached files will be stored.
     * 
     * @param dir absolute path of the directory
     */
    public void setSaveDirectory(String dir) {
        this.saveDirectory = dir;
    }

    /**
     * Downloads new messages and saves attachments to disk if any.
     * 
     * @param host
     * @param port
     * @param userName
     * @param password
     */
    public void downloadEmailAttachments(String host, String port, String userName, String password) {
        Properties properties = new Properties();

        // server setting
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", port);

        // SSL setting
        properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port", String.valueOf(port));

        Session session = Session.getDefaultInstance(properties);

        try {
            // connects to the message store
            Store store = session.getStore("imap");
            store.connect(userName, password);

            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_WRITE);

            // fetches new messages from server
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
            Message[] arrayMessages = folderInbox.search(unseenFlagTerm);
            System.out.println("num de fichier unread=" + arrayMessages.length);
            for (Message message : arrayMessages) {
                Address[] fromAddress = message.getFrom();
                String from = fromAddress[0].toString();
                String subject = message.getSubject();
                Date sentDate = message.getSentDate();

                String contentType = message.getContentType();
                String messageContent = "";

                // store attachment file name, separated by comma
                String attachFiles = "";
                if (contentType.contains("multipart")) {
                    // content may contain attachments
                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            // this part is attachment
                            String fileName = part.getFileName();
                            attachFiles += fileName + ", ";
                            part.saveFile(saveDirectory + File.separator + fileName);
                        } else {
                            // this part may be the message content
                            messageContent = part.getContent().toString();
                        }
                    }

                    if (attachFiles.length() > 1) {
                        attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                    }
                } else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                    Object content = message.getContent();
                    if (content != null) {
                        messageContent = content.toString();
                    }
                }

                // print out details of each message
                System.out.println("Message #");
                System.out.println("\t From: " + from);
                System.out.println("\t Subject: " + subject);
                System.out.println("\t Sent Date: " + sentDate);
                System.out.println("\t Message: " + messageContent);
                System.out.println("\t Attachments: " + attachFiles);

                message.setFlag(Flag.SEEN, true);
            }

            // disconnect
            folderInbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for imap.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    // private boolean isKDaysBefore(int k, Date sentDate) {
    // boolean result = false;
    // Calendar cal = Calendar.getInstance();
    // cal.setTime(new Date());
    // cal.add(Calendar.DAY_OF_YEAR, -k);
    // Date kDayBefore = cal.getTime();
    // System.out.println("------------" + kDayBefore);
    // if (sentDate.after(kDayBefore)) {
    // result = true;
    // }
    // return result;
    // }

    /**
     * Runs this program with Gmail POP3 server
     */
    public static void main(String[] args) {
        String host = "imap.havas.tools";
        String port = "993";
        String userName = "fr-hvmdata01@havas.tools";
        String password = "45gsd5Gd6G@";

        String saveDirectory = "C:/Attachment";

        EmailAttachmentReceiver receiver = new EmailAttachmentReceiver();
        receiver.setSaveDirectory(saveDirectory);
        receiver.downloadEmailAttachments(host, port, userName, password);

    }
}