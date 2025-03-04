import { createRoot } from 'react-dom/client'
import App from './App.tsx'

import { QueryClient, QueryClientProvider } from 'react-query';
import React from 'react';


// Crear el QueryClient
const queryClient = new QueryClient();


createRoot(document.getElementById('root')!).render(

<React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <App />
    </QueryClientProvider>
  </React.StrictMode>
)
