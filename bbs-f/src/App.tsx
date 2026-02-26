import './App.css'
import { Route, Routes } from 'react-router-dom'
import Login from './views/users/Login'
import Layout from './layout/Layout'

function App() {

  return (
    <Routes>
      <Route element={<Layout />}>
        <Route path="/bbs/user/loginView.do" element={<Login />} />
      </Route>
    </Routes>
  )
}

export default App
