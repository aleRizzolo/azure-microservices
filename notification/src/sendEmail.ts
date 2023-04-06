import * as dotenv from "dotenv"
import { EmailClient } from "@azure/communication-email"

dotenv.config()

const connectionString = process.env.COMMUNICATION_SERVICES_CONNECTION_STRING
const emailClient = new EmailClient(connectionString!)

export const sendEmail = async (email: string) => {
  const POLLER_WAIT_TIME = 10
  try {
    const message = {
      senderAddress: "",
      content: {
        subject: "Esame cloud 2023",
        plainText: "Registrazione avvenuta con successo",
      },
      recipients: {
        to: [
          {
            address: email,
          },
        ],
      },
    }

    const poller = await emailClient.beginSend(message)

    if (!poller.getOperationState().isStarted) {
      throw "Poller was not started."
    }

    let timeElapsed = 0
    while (!poller.isDone()) {
      poller.poll()

      await new Promise((resolve) => setTimeout(resolve, POLLER_WAIT_TIME * 1000))
      timeElapsed += 10

      if (timeElapsed > 18 * POLLER_WAIT_TIME) {
        throw "Polling timed out."
      }
    }
  } catch (e) {
    console.log(e)
  }
}
