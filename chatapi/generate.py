import openai
import json
import os

openai.api_key = "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"
reponse = openai.Completion.create(
    engine="davinci",
    prompt="请生成一份带有五个问题的社会调查问卷，带有选项",
    max_tokens=500,
    n=1,
    stop=None,
    temperature=0.5,
)

text = reponse['choices'][0]['text'].strip()

root_path = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
json_path = os.path.join(root_path, 'data', 'survey.json')

try:
    with open(json_path, 'w', encoding='utf-8') as f:
        json.dump(text, f, ensure_ascii=False, indent=4)
except FileNotFoundError:
    os.makedirs(os.path.dirname(json_path), exist_ok=True)
    with open(json_path, 'w', encoding='utf-8') as f:
        json.dump(text, f, ensure_ascii=False, indent=4)