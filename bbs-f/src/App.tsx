import './App.css'
import { Route, Routes } from 'react-router-dom'
import Login from './views/users/Login'
import Layout from './layout/Layout'
import Join from './views/users/Join'

function App() {

  return (
    <Routes>
      <Route element={<Layout />}>
        <Route path="/bbs/user/loginView.do" element={<Login />} />
        <Route path="/bbs/user/joinView.do" element={<Join />} />
      </Route>
    </Routes>
  )
}

export default App
