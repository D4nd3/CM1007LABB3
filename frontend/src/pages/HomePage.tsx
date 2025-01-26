import React from 'react';
import { Navbar } from '../components';

const HomePage: React.FC = () => {
  return (
    <div>
      <Navbar />
      <h2>Välkommen till startsidan!</h2>
      <p>Du är nu inloggad.</p>
    </div>
  );
};

export default HomePage;