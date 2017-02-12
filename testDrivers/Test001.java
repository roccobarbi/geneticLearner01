package geneticLearner01.testDrivers;

import geneticLearner01.*;

public class Test001 {

	public static void main(String[] args) {
		TrainingItem trainingSet [] = new TrainingItem[10];
		String words[] = {"prestito", "cessione", "sconto", "ultimo giorno", "extra", "chiaro", "conto", "estratto", "questionario", "bonus", "benvenuto", "abbonamento", "sconto", "gratis"};
		
		trainingSet[0] = new TrainingItem("Con la Cessione del Quinto riservata a dipendenti e pensionati Signor Prestito può aiutarti a ottenere un finanziamento comodo, sicuro e adatto alle tue esigenze per portare a termine i tuoi progetti e realizzare i tuoi sogni.", true);
		trainingSet[1] = new TrainingItem("-15%* EXTRA SUI SALDI\nULTIMI GIORNI DI OFFERTE ELETTRICHE", true);
		trainingSet[2] = new TrainingItem("Gentile Titolare,\nIl tuo Estratto Conto per Carta American Express è pronto per essere consultato in formato PDF all´interno dell´area protetta del sito American Express all´indirizzo: www.americanexpress.it/estrattoconto\nL´e-mail che ti notificherà la disponibilità dell´estratto conto, verrà utilizzata anche per comunicarti importanti aggiornamenti o informazioni sui Termini e Condizioni della Carta American Express. Troverai, quindi, queste informazioni nella versione PDF del tuo estratto conto.", false);
		trainingSet[3] = new TrainingItem("Gentile Cliente, \ndedicando pochi secondi alla compilazione di un questionario ci permetterà di essere constantemente informati circa i desideri e aspettative dei nostri Ospiti per offrire, giorno dopo giorno, un servizio migliore.\nLa Sua opinione è per noi preziosa!\nPuò compilare il questionario direttamente online a questo indirizzo:", false);
		trainingSet[4] = new TrainingItem("Buonasera Renato,\nmi spiace disturbarti a quest'ora, ma temo non mi sia ancora arrivato il test.\nPer la prossima settimana, ho verificato il calendario di lunedì e martedì: dovrei riuscire ad esserci lunedì dopo le 16.30 (idealmente alle 17) o martedì dalle 18.30.\nBuona serata,", false);
		trainingSet[5] = new TrainingItem("Ti informiamo che il tuo ordine è stato spedito ed è in transito. Da questo momento in poi non è più possibile apportare alcuna modifica. Se desideri restituire un articolo, visualizzare o modificare altri ordini, visita la sezione I miei ordini di Amazon.it.", false);
		trainingSet[6] = new TrainingItem("ciao Rocco\nmessaggio ricevuto forte e chiaro\nora non ci resta che trovare il modo di convincere i nostri amatissimi pargoli\na farne buon uso...   :-D\ngraaaazieee\nbaci e abbracci a Sara, Alessio e a tutta la compagnia", false);
		trainingSet[7] = new TrainingItem("Ciao rocco barbini,\nSu Svinando troverai i migliori vini con sconti fino al 50%. Per accedere alle offerte, conferma la tua registrazione cliccando qui sotto.\nConferma la tua registrazione e ottieni un buono sconto da 7 Euro", false);
		trainingSet[8] = new TrainingItem("Il vero amore ha bisogno di tempo\nIl giorno di San Valentino non basta. Per questo motivo ti diamo un intero mese di Premium a soli 0.99 €.\nTwoo Premium è un servizio di abbonamento. Il primo mese costerà 0.99 €. Dopo il primo mese di Premium ti verrà addebitato il normale costo di rinnovo di 9.99 € ogni 1 mese, a meno che tu non interrompa l'abbonamento, cosa che puoi fare in qualunque momento dalle tue Impostazioni. Hai ricevuto questa notifica perché sei registrato come Hinatasan", true);
		trainingSet[9] = new TrainingItem("Scommetti su tutte le partite di Serie A con il bonus di benvenuto di Unibet!\nRegistrati in due minuti per ricevere in esclusiva un bonus Sport da 60 Euro che ti permetterà di avere il 100% di bonus sul tuo primo deposito fino a 50 Euro più una Scommessa Senza Rischio mobile da 10 Euro!\nGiocando su Unibet.it hai a disposizione un ricco palinsesto di eventi sportivi su cui scommettere anche Live e da mobile e puoi beneficiare di tutti i Bonus e le Promozioni riservate ai clienti!", true);
		
		EvolutionEngine darwin001 = new EvolutionEngine(words);
		darwin001.setTrainingSet(trainingSet);
		
		System.out.println("Evolving...");
		RuleOrganism theRule = darwin001.evolve(0.2, 100000);
		System.out.println("Evolved.");
		
		int trainingSize = trainingSet.length, qtyFiltered = 0, qtyPassed = 0;
		for(int i = 0; i < trainingSize; i++){
			if(trainingSet[i].getIsFiltered())
				qtyFiltered++;
		}
		qtyPassed = trainingSize - qtyFiltered;
		
		int cn = 0, cf = 0;
		float fitness = 0;
		for(int t = 0; t < trainingSet.length; t++){
			System.out.println("Filtering: " + t);
			System.out.print("Was it correct?: ");
			if(theRule.filter(trainingSet[t].getText())){
				if(trainingSet[t].getIsFiltered()){
					System.out.println("YES");
					cf++;
				} else {
					System.out.println("NO");
					cn++;
				}
			} else {
				if(trainingSet[t].getIsFiltered()){
					System.out.println("NO");
				} else {
					System.out.println("YES");
				}
			}
		}
		fitness = ((float) cf / (float) qtyFiltered) - ((float) cn / (float) qtyPassed);
		
		System.out.println("Fitness: " + fitness);
		System.out.println("Qty filtered right: " + cf + " over a total of: " + qtyFiltered);
		System.out.println("Qty filtered wrong: " + cn + " over a total of: " + qtyPassed);
		
	}

}
