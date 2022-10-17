ALTER TABLE forma_pagamento ADD COLUMN data_atualizacao DATETIME NULL;
UPDATE forma_pagamento SET data_atualizacao = utc_timestamp();
ALTER TABLE forma_pagamento MODIFY data_atualizacao DATETIME NOT NULL;