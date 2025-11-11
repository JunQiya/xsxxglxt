<template>
  <!-- src/view/AI/index.vue -->

      <div class="chat-container">
        <div>
          <h2 style="text-align: center; margin: 20px 0;">AI智能问答助手</h2>
        </div>
  
        <!-- 聊天消息 -->
        <div class="chat-messages">
          <div v-for="(msg, index) in messages" :key="index" :class="['message', msg.role]">
            <div class="avatar">
              <img :src="msg.role === 'user' ? userAvatar : aiAvatar" alt="avatar">
            </div>
            <div class="content">{{ msg.content }}</div>
          </div>
          <div v-if="loading" class="loading-indicator">思考中...</div>
        </div>
    
  
        <!-- 少用点，测试一下，免费额度用完得扣钱 -->
  
  
        <!-- 用户输入 -->
        <div class="input-area">
          <input
            v-model="inputMessage"
            @keyup.enter="sendMessage"
            placeholder="输入您的问题..."
            :disabled="loading"
          />
          <button @click="sendMessage" :disabled="!loading && !inputMessage">
            {{ loading ? '取消' : '发送' }}
          </button>
        </div>
      </div>
  </template>
  
  <!-- 支持多轮对话，目前模型DeepseekV3，联网搜索，单个页面可完成AI调用,无需后端-->
  
  <script>
  export default {
    data() {
      return {
        messages: [],
        inputMessage: '',
        loading: false,
        // userAvatar: require('@/assets/image/user.png'), // 这个地方在控制台报错，用CDN地址搞一个用户头像也许可以解决
        aiAvatar: 'http://xsxx.lxxno.cn/2025xszhwz/O1CN01Pda9nq1YDV0mnZ31H_!!6000000003025-54-tps-120-120.apng',
        sessionId: null,
        abortController: null,
        isAborted: false,
        currentReader: null
      }
    },
    methods: {
      async sendMessage() {
        if (this.loading) {
          this.abortController?.abort();
          this.isAborted = true;
          if (this.currentReader) {
            await this.currentReader.cancel();
          }
          return;
        }
  
        if (!this.inputMessage.trim()) return;
  
        try {
          this.loading = true;
          this.isAborted = false;
          this.messages.push({ role: 'user', content: this.inputMessage });
  
          const apiKey = 'sk-8dcb7c7b8a054cb8904ca7eca58dae62';
          const appId = '7991bf8e5def42c1b2f78f21b7378553';
          const url = `https://dashscope.aliyuncs.com/api/v1/apps/${appId}/completion`;
  
          this.abortController = new AbortController();
          const response = await fetch(url, {
            method: 'POST',
            headers: {
              'Authorization': `Bearer ${apiKey}`,
              'Content-Type': 'application/json',
              'X-DashScope-SSE': 'enable'
            },
            body: JSON.stringify({
              input: {
                prompt: this.inputMessage.trim(),
                session_id: this.sessionId,
              },
              parameters: {
                'incremental_output': 'true'
              },
              debug: {}
            }),
            signal: this.abortController.signal
          });
  
          if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
  
          let fullResponse = '';
          let buffer = '';
          const assistantMessageIndex = this.messages.push({
            role: 'assistant',
            content: ''
          }) - 1;
  
          const reader = response.body.getReader();
          this.currentReader = reader;
          const decoder = new TextDecoder('utf-8');
  
          const processStream = async ({ done, value }) => {
            if (done || this.isAborted) {
              this.loading = false;
              return;
            }
  
            buffer += decoder.decode(value);
            const lines = buffer.split('\n');
            buffer = lines.pop() || '';
  
            for (const line of lines) {
              if (line.startsWith('data:')) {
                try {
                  const eventData = JSON.parse(line.slice(5).trim());
                  if (eventData.output?.text) {
                    fullResponse += eventData.output.text;
                    this.$set(this.messages, assistantMessageIndex, {
                      ...this.messages[assistantMessageIndex],
                      content: fullResponse
                    });
                    this.sessionId = eventData.output.session_id;
                  }
                } catch (e) {
                  console.error('解析错误:', e);
                }
              }
            }
  
            reader.read().then(processStream);
          };
  
          reader.read().then(processStream).catch(error => {
            if (error.name !== 'AbortError') {
              console.error('流读取错误:', error);
            }
          });
  
        } catch (error) {
          this.loading = false;
          if (error.name === 'AbortError' && this.isAborted) {
            this.messages.push({ role: 'system', content: '已取消生成' });
          } else {
            console.error(`Error calling DashScope: ${error.message}`);
            this.messages.push({ role: 'system', content: '请求出错，请稍后重试' });
          }
        } finally {
          this.abortController = null;
          if (!this.isAborted) {
            this.inputMessage = '';
          }
        }
      }
    }
  }
  </script>
  
  <style scoped>

    .chat-container {
      max-width: 1400px;
      margin: 20px auto;
      border: 1px solid #e4e7ed;
      border-radius: 8px;
      overflow: hidden;
    }
    
    .chat-messages {
      height: 600px;
      overflow-y: auto;
      padding: 20px;
      background: #f8f9fa;
    }
    
    .message {
      display: flex;
      gap: 15px;
      margin-bottom: 20px;
    }
    
    .message.user {
      flex-direction: row-reverse;
    }
    
    .avatar img {
      width: 40px;
      height: 40px;
      border-radius: 50%;
    }
    
    .content {
      max-width: 70%;
      padding: 12px 16px;
      border-radius: 12px;
      background: white;
      box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    }
    
    .message.assistant .content {
      background: #1464E4;
      color: white;
    }
    
    .input-area {
      display: flex;
      padding: 20px;
      background: white;
      border-top: 1px solid #e4e7ed;
    }
    
    input {
      flex: 1;
      padding: 12px;
      border: 1px solid #e4e7ed;
      border-radius: 6px;
      margin-right: 10px;
    }
    
    button {
      padding: 12px 24px;
      background: #1464E4;
      color: white;
      border: none;
      border-radius: 6px;
      cursor: pointer;
    }
    
    button:disabled {
      background: #a0c0f0;
      cursor: not-allowed;
    }
    
    .loading-indicator {
      text-align: center;
      color: #666;
      padding: 10px;
    }
  </style>