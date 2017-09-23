import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.amazon.speech.ui.SsmlOutputSpeech;

import parliament2profile.Parliament;

public class AngekreuztSpeechlet implements Speechlet {

	private static final Logger log = LoggerFactory.getLogger(AngekreuztSpeechlet.class);
	private static String first = "Willkommen, du kannst nach Parteien, Themen und Kandidaten fragen";
	private static String second = "zum Beispiel";
	private static String third = "was denkt die Partei X zum Thema Integration,Sicherheit, E U Aussenpolitik, Bildung, Arbeit, Finanzen und Steuern?";
	private static String fourth = "Du kannst auch jeden Direktkandidaten zu den Themen fragen";
	private static String firstRe = "Es sind die Themen Bildung, Soziales, Integration, Aussenpolitik, Sicherheit und Steuern und Finanzen erfasst";

	public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
		return getWelcomeResponse();
	}

	/**
	 * Liest einen "Willkommen bei angekreuzt"-String, wenn der User den Skill
	 * startet.
	 * 
	 * @return
	 */
	private SpeechletResponse getWelcomeResponse() {

		String output = SpeechHelper.wrapInSpeak(first);

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Angekreuzt");
		card.setContent(first + second + third + fourth);

		return newAskResponse(output, true, longRepromptString());
	}

	/**
	 * Die drei unterschiedlichen Intents werden in drei Bedinungen abgefragt:
	 * Das Intent "wahlsys" übergibt den Slottypen "wahlsystem" als String an
	 * die Methode "auswaehlen in der Klasse "Wahlthema" und ruft diese auf.
	 * jeder fall ruft eine eigene newAskResponse auf bei Alexa cancel oder stop
	 * wird beendet bei Nichterkennugn wird eine Hilfestellug ausgegeben.
	 * 
	 * @param arg0
	 *            IntentRequest pipa
	 * @return SpeechletResponse for out to Alexa
	 */
	public SpeechletResponse onIntent(IntentRequest arg0, Session arg1) throws SpeechletException {
		// define Themen
		Themen themes = new Themen();
		// get intentName
		Intent intent = arg0.getIntent();
		String intentName = intent.getName();

		String result = "";

		if (intentName.equals("wahlsys")) {
			result = Wahlsystem.getText();

			SpeechletResponse response = newAskResponse(result, true, shortRepromptWahlsysString());
			// response.setShouldEndSession(true);
			return response;

		} else if (intentName.equals("zweitstimme")) {

			String themen = intent.getSlot("themen").getValue();
			String partei = intent.getSlot("partei").getValue();

			if ((themen != null && !themen.isEmpty()) && (partei != null && !partei.isEmpty())) {// slots
																									// recognized

				if (themes.mapping.mapCategoryToGroup.get(themen) != null) {

					result = ZweitStimme.auswahl(themen, partei, themes.mapping);

					SpeechletResponse response = newAskResponse(result, false, shortRepromptZweitstimmeString());
					// response.setShouldEndSession(true);
					return response;

				} else {
					SpeechletResponse response = newAskResponse(
							SpeechHelper.wrapInSpeak("Bitte versuche es noch einmal"), true,
							shortRepromptZweitstimmeString());
					// response.setShouldEndSession(true);
					return response;
				}

			} else {// some slot is empty
				SpeechletResponse response = newAskResponse(SpeechHelper.wrapInSpeak("Bitte versuche es noch einmal"),
						true, shortRepromptZweitstimmeString());
				// response.setShouldEndSession(true);
				return response;
			}

		} else if (intentName.equals("erststimme")) {
			String themen = intent.getSlot("themen").getValue();
			String fullname = intent.getSlot("kandidat").getValue();
			SpeechletResponse response;

			if ((themen != null && !themen.isEmpty()) && (fullname != null && !fullname.isEmpty())) {// slots
																										// recognized

				try {

					if (themes.mapping.mapCategoryToGroup.get(themen) != null) {

						Parliament parliament = new Parliament();
						parliament.setName("Bundestag");
						Erststimme erstestimme = new Erststimme(parliament.getName(), themes.mapping);

						response = newAskResponse(erstestimme.call(themen, fullname), true,
								shortRepromptErststimmeString());
						// response.setShouldEndSession(true);
						return response;

					} else {
						response = newAskResponse(SpeechHelper.wrapInSpeak("Bitte versuche es noch einmal"), true,
								shortRepromptZweitstimmeString());
						// response.setShouldEndSession(true);
						return response;
					}

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				response = newAskResponse(SpeechHelper.wrapInSpeak("Bitte versuche es noch einmal"), true,
						shortRepromptErststimmeString());
				// response.setShouldEndSession(true);
				return response;

			} else { // some slot is empty

				response = newAskResponse(SpeechHelper.wrapInSpeak("Bitte versuche es noch einmal"), true,
						shortRepromptErststimmeString());
				// response.setShouldEndSession(true);
				return response;
			}

		} else if (intentName.equals("AMAZON.HelpIntent")) {

			String speechOutput = "Du hast prinzipiell drei Möglichkeiten" + SpeechHelper.createBreak(1)
					+ "Erstens kannst Du dich über das Wahlsystem in Deutschland informieren"
					+ SpeechHelper.createBreak(1)
					+ "Zweitens kannst Du nach der Meinung einer Partei zu einem der genannten Themen fragen"
					+ SpeechHelper.createBreak(1)
					+ "Drittens kannst Du Dich nach der Meinung jedes Direktkandidaten zu den Themen erkundigen."
					+ SpeechHelper.createBreak(1)
					+ "zum Beispiel, was denkt die Partei X oder Kandidat Y zum Thema Integration, Sicherheit, E U Aussenpolitik, Bildung, Arbeit, Finanzen und Steuern?"
					+ SpeechHelper.createBreak(1) + "Was möchtest du wissen?";
			String repromptText = SpeechHelper.wrapInSpeak("Bitte stelle eine Frage");
			SpeechletResponse response = newAskResponse(SpeechHelper.wrapInSpeak(speechOutput), true, repromptText);
			// response.setShouldEndSession(false);
			return response;

		} else if (intentName.equals("AMAZON.StopIntent") || intentName.equals("AMAZON.CancelIntent")) {

			PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
			outputSpeech.setText("Servus");
			return SpeechletResponse.newTellResponse(outputSpeech);

		} else {
			String speechOutput = second + SpeechHelper.createBreak(1) + third + SpeechHelper.createBreak(1) + fourth
					+ SpeechHelper.createBreak(1);
			String repromptText = SpeechHelper.wrapInSpeak("Bitte stelle eine Frage");
			SpeechletResponse response = newAskResponse(SpeechHelper.wrapInSpeak(speechOutput), true, repromptText);
			// response.setShouldEndSession(true);
			return response;
		}
	}

	/**
	 * Wrapper for creating the Ask response from the input strings.
	 * 
	 * @param stringOutput
	 *            the output to be spoken
	 * @param isOutputSsml
	 *            whether the output text is of type SSML
	 * @param repromptText
	 *            the reprompt for if the user doesn't reply or is
	 *            misunderstood.
	 * @param isRepromptSsml
	 *            whether the reprompt text is of type SSML
	 * @return SpeechletResponse the speechlet response
	 */
	private SpeechletResponse newAskResponse(String stringOutput, boolean isOutputSsml, String repromptText) {
		boolean isRepromptSsml = true;
		OutputSpeech outputSpeech, repromptOutputSpeech;
		if (isOutputSsml) {
			outputSpeech = new SsmlOutputSpeech();
			((SsmlOutputSpeech) outputSpeech).setSsml(stringOutput);
		} else {
			outputSpeech = new PlainTextOutputSpeech();
			((PlainTextOutputSpeech) outputSpeech).setText(stringOutput);
		}

		if (isRepromptSsml) {
			repromptOutputSpeech = new SsmlOutputSpeech();
			((SsmlOutputSpeech) repromptOutputSpeech).setSsml(repromptText);
		} else {
			repromptOutputSpeech = new PlainTextOutputSpeech();
			((PlainTextOutputSpeech) repromptOutputSpeech).setText(repromptText);
		}
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(repromptOutputSpeech);
		return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
	}

	/**
	 * @return
	 */
	private String longRepromptString() {
		// Create reprompt
		String nice = "Schön, dass du dich über die Wahl am 24.September informieren willst. Das ist Super. Wähle ja keinen Schmarrn.";
		String secondRe = "Ok also, frag mich zum Beispiel wie funktioniert die Wahl in Deutschland oder frage zum Beispiel was denkt Kandidat X über Bildung, Arbeit und Soziales, Integration und "
				+ "Asyl, E U und Aussenpolitik, Sicherheit und Steuern und Finanzen.";
		String thirdRe = "Oder denk an deine Zweitstimme und frage ganz einfach:"
				+ "was sagt die Partei Y über das Thema Bildung, Sicherheit, Steuern und Finanzen, Integration und Asyl"
				+ ", E U und Aussenpolitik und Arbeit und Soziales.";
		String fourthRe = "Du kannst also direkt alle Kandidaten aller Wahlkreise oder die Parteien befragen. Yeah man.";

		String gegenantwort = SpeechHelper.wrapInSpeak(nice + SpeechHelper.createBreak(1) + secondRe
				+ SpeechHelper.createBreak(1) + thirdRe + SpeechHelper.createBreak(1) + fourthRe);
		return gegenantwort;
	}

	private String shortRepromptErststimmeString() {
		// Create reprompt
		String secondRe = "Möchtest Du mehr erfahren?";
		String fourthRe = "Bitte frage nach einem Direktkandidaten und einem Thema.";
		String gegenantwort = SpeechHelper.wrapInSpeak( secondRe + SpeechHelper.createBreak(1) + fourthRe);
		return gegenantwort;
	}

	private String shortRepromptZweitstimmeString() {
		// Create reprompt
		String secondRe = "Möchtest Du mehr erfahren?";
		String fourthRe = "Bitte frage nach einer Partei und einem Thema.";
		String gegenantwort = SpeechHelper.wrapInSpeak( secondRe + SpeechHelper.createBreak(1) + fourthRe);
		return gegenantwort;
	}

	private String shortRepromptWahlsysString() {
		String gegenantwort = SpeechHelper
				.wrapInSpeak("Möchtes Du es noch einmal hören? " + SpeechHelper.createBreak(1) + " Dann frage nochmal nach dem Wahlsystem.");
		return gegenantwort;
	}

	public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
		log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
	}

	public void onSessionStarted(SessionStartedRequest arg0, Session arg1) throws SpeechletException {
	}

}
