import telegram
import sys

# you can create a BotFather on telegram
# he will tell you your bot token.
bot_token = 'YOUR_BOT_TOKEN'

# you can find out your chat id by sending a message to your bot
# and then checking 
# https://api.telegram.org/bot<YOUR_BOT_TOKEN>/getUpdates
default_chat_id = 'YOUR_CHAT_ID'

def send(message, chat_id=default_chat_id, token=bot_token):
	bot = telegram.Bot(token=token)
	bot.sendMessage(chat_id=chat_id, text=message)

send(*sys.argv[1:])