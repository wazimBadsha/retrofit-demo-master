class Api::ProfilesController < Api::ApiController

  # GET /profiles
  def index
    @profiles = Profile.all.order('name ASC')
  end

  # POST /profiles
  def create
    @profile = Profile.create(create_params)
    if @profile.save
      render status: :created
    else
      render_errors_for @profile
    end
  end

  private

  def create_params
    params.permit(:name, :email, :phone)
  end

end