Rails.application.routes.draw do
  scope module: :api, :defaults => { format: 'json'} do
    resources :profiles, only: [:index, :create]
  end
end
