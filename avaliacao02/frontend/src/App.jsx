import React, { useState, useEffect, useCallback, useMemo } from 'react';
import './App.css';

function Modal({ isOpen, onClose, title, children }) {
  if (!isOpen) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h3>{title}</h3>
          <button className="btn btn-secondary btn-small" onClick={onClose}>
            X
          </button>
        </div>
        <div className="modal-body">{children}</div>
      </div>
    </div>
  );
}

function LoginScreen({ onLogin, loading, error }) {
  const [username, setUsername] = useState('user');
  const [password, setPassword] = useState('password');

  const handleSubmit = (e) => {
    e.preventDefault();
    onLogin(username, password);
  };

  return (
    <div className="login-container">
      <div className="login-header">
        <h1>Sistema Acadêmico</h1>
      </div>
      <form className="login-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="username">Usuário</label>
          <input
            type="text"
            id="username"
            className="form-input"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Senha</label>
          <input
            type="password"
            id="password"
            className="form-input"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        {error && <div className="error-message">{error}</div>}
        <button type="submit" className="btn btn-primary" disabled={loading}>
          {loading ? 'Entrando...' : 'Entrar'}
        </button>
      </form>
    </div>
  );
}

function NavBar({ page, setPage, onLogout }) {
  return (
    <nav className="navbar">
      <div className="navbar-links">
        <button
          className={page === 'alunos' ? 'active' : ''}
          onClick={() => setPage('alunos')}
        >
          Alunos
        </button>
        <button
          className={page === 'cursos' ? 'active' : ''}
          onClick={() => setPage('cursos')}
        >
          Cursos
        </button>
      </div>
      <button className="btn btn-secondary" onClick={onLogout}>
        Sair
      </button>
    </nav>
  );
}

function AlunoForm({ aluno, onSave, onClose, loading }) {
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [matricula, setMatricula] = useState('');

  useEffect(() => {
    if (aluno) {
      setNome(aluno.nome);
      setEmail(aluno.email);
      setMatricula(aluno.matricula);
    } else {
      setNome('');
      setEmail('');
      setMatricula('');
    }
  }, [aluno]);

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave({ nome, email, matricula });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-group">
        <label htmlFor="nome">Nome</label>
        <input
          id="nome"
          type="text"
          className="form-input"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          required
        />
      </div>
      <div className="form-group">
        <label htmlFor="email">Email</label>
        <input
          id="email"
          type="email"
          className="form-input"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
      </div>
      <div className="form-group">
        <label htmlFor="matricula">Matrícula</label>
        <input
          id="matricula"
          type="text"
          className="form-input"
          value={matricula}
          onChange={(e) => setMatricula(e.target.value)}
          required
        />
      </div>
      <div className="modal-footer">
        <button
          type="button"
          className="btn btn-secondary"
          onClick={onClose}
          disabled={loading}
        >
          Cancelar
        </button>
        <button type="submit" className="btn btn-primary" disabled={loading}>
          {loading ? 'Salvando...' : 'Salvar'}
        </button>
      </div>
    </form>
  );
}

function CursoForm({ curso, onSave, onClose, loading }) {
  const [nome, setNome] = useState('');
  const [cargaHoraria, setCargaHoraria] = useState('');

  useEffect(() => {
    if (curso) {
      setNome(curso.nome);
      setCargaHoraria(curso.cargaHoraria);
    } else {
      setNome('');
      setCargaHoraria('');
    }
  }, [curso]);

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave({ nome, cargaHoraria: parseInt(cargaHoraria, 10) });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-group">
        <label htmlFor="nome">Nome do Curso</label>
        <input
          id="nome"
          type="text"
          className="form-input"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          required
        />
      </div>
      <div className="form-group">
        <label htmlFor="cargaHoraria">Carga Horária (horas)</label>
        <input
          id="cargaHoraria"
          type="number"
          className="form-input"
          value={cargaHoraria}
          onChange={(e) => setCargaHoraria(e.target.value)}
          required
        />
      </div>
      <div className="modal-footer">
        <button
          type="button"
          className="btn btn-secondary"
          onClick={onClose}
          disabled={loading}
        >
          Cancelar
        </button>
        <button type="submit" className="btn btn-primary" disabled={loading}>
          {loading ? 'Salvando...' : 'Salvar'}
        </button>
      </div>
    </form>
  );
}

function MatricularModal({ aluno, cursos, onClose, onMatricular, loading }) {
  const [cursoId, setCursoId] = useState('');

  const cursosDisponiveis =
    cursos.filter(
      (c) => !aluno.cursos.find((ac) => ac.id === c.id)
    ) || [];

  useEffect(() => {
    if (cursosDisponiveis.length > 0) {
      setCursoId(cursosDisponiveis[0].id);
    } else {
      setCursoId('');
    }
  }, [aluno, cursosDisponiveis]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (cursoId) {
      onMatricular(aluno.id, cursoId);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <p>
        Selecione um curso para matricular <strong>{aluno.nome}</strong>:
      </p>
      {cursosDisponiveis.length > 0 ? (
        <div className="form-group">
          <label htmlFor="curso-select">Cursos Disponíveis</label>
          <select
            id="curso-select"
            className="form-select"
            value={cursoId}
            onChange={(e) => setCursoId(e.target.value)}
          >
            {cursosDisponiveis.map((c) => (
              <option key={c.id} value={c.id}>
                {c.nome} (Carga: {c.cargaHoraria}h)
              </option>
            ))}
          </select>
        </div>
      ) : (
        <p>Este aluno já está matriculado em todos os cursos disponíveis.</p>
      )}
      <div className="modal-footer">
        <button
          type="button"
          className="btn btn-secondary"
          onClick={onClose}
          disabled={loading}
        >
          Cancelar
        </button>
        <button
          type="submit"
          className="btn btn-primary"
          disabled={loading || cursosDisponiveis.length === 0}
        >
          {loading ? 'Matriculando...' : 'Matricular'}
        </button>
      </div>
    </form>
  );
}

function AlunosPage({ api, data, cursos, onDataChange }) {
  const { alunos, loading, error } = data;
  const [modal, setModal] = useState({
    form: false,
    matricular: false,
  });
  const [alunoSelecionado, setAlunoSelecionado] = useState(null);
  const [opLoading, setOpLoading] = useState(false);

  const handleSave = async (alunoData) => {
    setOpLoading(true);
    try {
      if (alunoSelecionado) {
        await api.put(`/api/alunos/${alunoSelecionado.id}`, alunoData);
      } else {
        await api.post('/api/alunos', alunoData);
      }
      onDataChange();
      handleCloseModals();
    } catch (err) {
      console.error(err);
    } finally {
      setOpLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja excluir este aluno?')) {
      try {
        await api.del(`/api/alunos/${id}`);
        onDataChange();
      } catch (err) {
        console.error(err);
      }
    }
  };

  const handleMatricular = async (alunoId, cursoId) => {
    setOpLoading(true);
    try {
      await api.post(`/api/alunos/${alunoId}/matricular/${cursoId}`, {});
      onDataChange();
      handleCloseModals();
    } catch (err) {
      console.error(err);
    } finally {
      setOpLoading(false);
    }
  };

  const handleCloseModals = () => {
    setModal({ form: false, matricular: false });
    setAlunoSelecionado(null);
  };

  return (
    <div>
      <div className="page-header">
        <h2>Alunos</h2>
        <button
          className="btn btn-primary"
          onClick={() => {
            setAlunoSelecionado(null);
            setModal({ ...modal, form: true });
          }}
        >
          + Cadastrar Aluno
        </button>
      </div>

      {loading && <div className="loading">Carregando...</div>}
      {error && <div className="error-message">{error}</div>}

      <ul className="content-list">
        {!loading &&
          alunos.length > 0 &&
          alunos.map((aluno) => (
            <li key={aluno.id} className="list-item">
              <div className="list-item-info">
                <strong>{aluno.nome}</strong>
                <span>Matrícula: {aluno.matricula}</span>
                <span>Email: {aluno.email}</span>
                <div className="cursos-matriculados">
                  {aluno.cursos.length > 0 ? (
                    aluno.cursos.map((c) => <span key={c.id}>{c.nome}</span>)
                  ) : (
                    <em>Nenhum curso matriculado.</em>
                  )}
                </div>
              </div>
              <div className="list-item-actions">
                <button
                  className="btn btn-secondary btn-small"
                  onClick={() => {
                    setAlunoSelecionado(aluno);
                    setModal({ ...modal, matricular: true });
                  }}
                >
                  Matricular
                </button>
                <button
                  className="btn btn-secondary btn-small"
                  onClick={() => {
                    setAlunoSelecionado(aluno);
                    setModal({ ...modal, form: true });
                  }}
                >
                  Editar
                </button>
                <button
                  className="btn btn-danger btn-small"
                  onClick={() => handleDelete(aluno.id)}
                >
                  Excluir
                </button>
              </div>
            </li>
          ))}
        {!loading && alunos.length === 0 && (
          <div className="empty-message">Nenhum aluno cadastrado.</div>
        )}
      </ul>

      <Modal
        isOpen={modal.form}
        onClose={handleCloseModals}
        title={alunoSelecionado ? 'Editar Aluno' : 'Cadastrar Aluno'}
      >
        <AlunoForm
          aluno={alunoSelecionado}
          onSave={handleSave}
          onClose={handleCloseModals}
          loading={opLoading}
        />
      </Modal>

      <Modal
        isOpen={modal.matricular && alunoSelecionado != null}
        onClose={handleCloseModals}
        title="Matricular Aluno"
      >
        {alunoSelecionado && (
          <MatricularModal
            aluno={alunoSelecionado}
            cursos={cursos}
            onClose={handleCloseModals}
            onMatricular={handleMatricular}
            loading={opLoading}
          />
        )}
      </Modal>
    </div>
  );
}

function CursosPage({ api, data, onDataChange }) {
  const { cursos, loading, error } = data;
  const [modalOpen, setModalOpen] = useState(false);
  const [cursoSelecionado, setCursoSelecionado] = useState(null);
  const [opLoading, setOpLoading] = useState(false);

  const handleSave = async (cursoData) => {
    setOpLoading(true);
    try {
      if (cursoSelecionado) {
        await api.put(`/api/cursos/${cursoSelecionado.id}`, cursoData);
      } else {
        await api.post('/api/cursos', cursoData);
      }
      onDataChange();
      handleCloseModals();
    } catch (err) {
      console.error(err);
    } finally {
      setOpLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja excluir este curso?')) {
      try {
        await api.del(`/api/cursos/${id}`);
        onDataChange();
      } catch (err) {
        console.error(err);
      }
    }
  };

  const handleCloseModals = () => {
    setModalOpen(false);
    setCursoSelecionado(null);
  };

  return (
    <div>
      <div className="page-header">
        <h2>Cursos</h2>
        <button
          className="btn btn-primary"
          onClick={() => {
            setCursoSelecionado(null);
            setModalOpen(true);
          }}
        >
          + Cadastrar Curso
        </button>
      </div>

      {loading && <div className="loading">Carregando...</div>}
      {error && <div className="error-message">{error}</div>}

      <ul className="content-list">
        {!loading &&
          cursos.length > 0 &&
          cursos.map((curso) => (
            <li key={curso.id} className="list-item">
              <div className="list-item-info">
                <strong>{curso.nome}</strong>
                <span>Carga Horária: {curso.cargaHoraria} horas</span>
              </div>
              <div className="list-item-actions">
                <button
                  className="btn btn-secondary btn-small"
                  onClick={() => {
                    setCursoSelecionado(curso);
                    setModalOpen(true);
                  }}
                >
                  Editar
                </button>
                <button
                  className="btn btn-danger btn-small"
                  onClick={() => handleDelete(curso.id)}
                >
                  Excluir
                </button>
              </div>
            </li>
          ))}
        {!loading && cursos.length === 0 && (
          <div className="empty-message">Nenhum curso cadastrado.</div>
        )}
      </ul>

      <Modal
        isOpen={modalOpen}
        onClose={handleCloseModals}
        title={cursoSelecionado ? 'Editar Curso' : 'Cadastrar Curso'}
      >
        <CursoForm
          curso={cursoSelecionado}
          onSave={handleSave}
          onClose={handleCloseModals}
          loading={opLoading}
        />
      </Modal>
    </div>
  );
}

function App() {
  const [page, setPage] = useState('auth');
  const [auth, setAuth] = useState(null);
  const [authLoading, setAuthLoading] = useState(false);
  const [authError, setAuthError] = useState(null);

  const [alunos, setAlunos] = useState([]);
  const [cursos, setCursos] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const apiRequest = useCallback(
    async (url, options = {}) => {
      if (!auth) throw new Error('Não autenticado');

      const headers = new Headers(options.headers || {});
      headers.set(
        'Authorization',
        'Basic ' + btoa(`${auth.username}:${auth.password}`)
      );
      if (options.body) {
        headers.set('Content-Type', 'application/json');
      }

      const response = await fetch(url, { ...options, headers });

      if (response.status === 401) {
        setAuth(null);
        setPage('auth');
        setAuthError('Sessão expirada. Por favor, faça login novamente.');
        throw new Error('Não autorizado');
      }
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      if (response.status !== 204) {
        return response.json();
      }
      return null;
    },
    [auth]
  );

  const api = useMemo(() => ({
    get: (url) => apiRequest(url),
    post: (url, body) => apiRequest(url, { method: 'POST', body: JSON.stringify(body) }),
    put: (url, body) => apiRequest(url, { method: 'PUT', body: JSON.stringify(body) }),
    del: (url) => apiRequest(url, { method: 'DELETE' }),
  }), [apiRequest]);

  const fetchAlunos = useCallback(async (api) => {
    setLoading(true);
    setError(null);
    try {
      const data = await api.get('/api/alunos');
      setAlunos(data);
    } catch (err) {
      if (err.message !== 'Não autorizado') {
        setError('Falha ao buscar alunos.');
      }
    } finally {
      setLoading(false);
    }
  }, []);

  const fetchCursos = useCallback(async (api) => {
    setLoading(true);
    setError(null);
    try {
      const data = await api.get('/api/cursos');
      setCursos(data);
    } catch (err) {
      if (err.message !== 'Não autorizado') {
        setError('Falha ao buscar cursos.');
      }
    } finally {
      setLoading(false);
    }
  }, []);

  const handleLogin = async (username, password) => {
    setAuthLoading(true);
    setAuthError(null);
    try {
      const headers = new Headers();
      headers.set(
        'Authorization',
        'Basic ' + btoa(`${username}:${password}`)
      );
      const response = await fetch('/api/alunos', { headers });

      if (response.status === 401) {
        throw new Error('Usuário ou senha inválidos.');
      }
      if (!response.ok) {
        throw new Error('Erro ao tentar conectar.');
      }
      
      setAuth({ username, password });
      setPage('alunos');
    } catch (err) {
      setAuthError(err.message);
    } finally {
      setAuthLoading(false);
    }
  };

  const handleLogout = () => {
    setAuth(null);
    setPage('auth');
    setAlunos([]);
    setCursos([]);
    setAuthError(null);
  };

  useEffect(() => {
    if (auth) {
      const api = {
        get: (url) => apiRequest(url),
        post: (url, body) => apiRequest(url, { method: 'POST', body: JSON.stringify(body) }),
        put: (url, body) => apiRequest(url, { method: 'PUT', body: JSON.stringify(body) }),
        del: (url) => apiRequest(url, { method: 'DELETE' }),
      };
      
      if (page === 'alunos') {
        fetchAlunos(api);
        fetchCursos(api);
      }
      if (page === 'cursos') {
        fetchCursos(api);
      }
    }
  }, [page, auth, apiRequest, fetchAlunos, fetchCursos]);
  
    const onDataChangeAlunos = () => {
    fetchAlunos(api);
    fetchCursos(api);
  };

  const onDataChangeCursos = () => {
    fetchCursos(api);
  };

  const renderPage = () => {
    switch (page) {
      case 'alunos':
        return (
          <AlunosPage
            api={api}
            data={{ alunos, loading, error }}
            cursos={cursos}
            onDataChange={onDataChangeAlunos}
          />
        );
      case 'cursos':
        return (
          <CursosPage
            api={api}
            data={{ cursos, loading, error }}
            onDataChange={onDataChangeCursos}
          />
        );
      case 'auth':
      default:
        return (
          <LoginScreen
            onLogin={handleLogin}
            loading={authLoading}
            error={authError}
          />
        );
    }
  };

  return (
    <div className="app-container">
      {page !== 'auth' && <NavBar page={page} setPage={setPage} onLogout={handleLogout} />}
      {renderPage()}
    </div>
  );
}

export default App;